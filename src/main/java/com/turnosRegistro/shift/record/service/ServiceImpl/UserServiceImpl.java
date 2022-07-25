package com.turnosRegistro.shift.record.service.ServiceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turnosRegistro.shift.record.config.MessageHandler;
import com.turnosRegistro.shift.record.config.PaginationMessageHandler;
import com.turnosRegistro.shift.record.formsAndResponses.ContactEmailForm;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.formsAndResponses.RefreshTokenForm;
import com.turnosRegistro.shift.record.dto.UserDto;
import com.turnosRegistro.shift.record.formsAndResponses.UserLoginResponse;
import com.turnosRegistro.shift.record.dto.mapper.UserMapper;
import com.turnosRegistro.shift.record.enums.Role;
import com.turnosRegistro.shift.record.exception.*;
import com.turnosRegistro.shift.record.model.Company;
import com.turnosRegistro.shift.record.model.User;
import com.turnosRegistro.shift.record.repository.UserRepository;
import com.turnosRegistro.shift.record.service.EmailService;
import com.turnosRegistro.shift.record.service.TurnNotAvailableService;
import com.turnosRegistro.shift.record.service.UserService;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final int SIZE_PAGE = 10;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private PaginationMessageHandler paginationMessage;
    @Autowired
    private AuthenticationManager authenticationManager;

    private MessagePagination messagePagination;
    @Autowired
    private TurnNotAvailableService turnNotAvailableService;
    @Autowired
    private EmailService emailService;
    @Override
    public UserDto createUser(UserDto userDto) {
        return userMapper.entityToDto(userRepository.save(userMapper.entityCreateFromDto(userDto)));
    }
    @Override
    public UserDto updateUser(Long idUser, UserDto userDto, HttpServletRequest request) {
        User user = userMapper.updateEntityFromDto(findUserEntityById(idUser), userDto);
        isAuthorizateOnlyUserCreatorAndRolAdmin(user, request);
        return userMapper.entityToDto(userRepository.save(user));
    }

    @Override
    public User findUserEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException(messageHandler.message("not.found", String.valueOf(id))));
    }

    @Override
    public UserDto findUserDtoById(Long id) {
        return userMapper.entityToDto(findUserEntityById(id));
    }

    @Override
    public MessageInfo deleteUserById(Long id, HttpServletRequest request) {
        User user = findUserEntityById(id);
        isAuthorizateOnlyUserCreatorAndRolAdmin(user, request);
        user.getReserveFavorite().stream().forEach(r-> turnNotAvailableService.deleteEntityByReserve(r));
        userRepository.delete(user);
        return new MessageInfo(messageHandler.message("delete", "the user: " + user.getEmail()), HttpStatus.OK.value(), request.getRequestURL().toString());
    }

    @Override
    public MessagePagination findUsersDtoPagination(int page, HttpServletRequest request) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, SIZE_PAGE));
        return paginationMessage.message(userPage, userMapper.ListDtoFromEntities(userPage.getContent()), request);
    }

    @Override
    public Authentication authenticationFilter(String email, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication autenticacionFilter = authenticationManager.authenticate(authenticationToken);
        return autenticacionFilter;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email);
        Collection<SimpleGrantedAuthority> authorizations = Collections.singleton(new SimpleGrantedAuthority(user.getRole().getAuthority()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorizations);
    }
    @Override
    public UserLoginResponse userLogin(String email, String password, HttpServletRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = findUserByEmail(email);
        if(password == null) throw new BadRequestException(messageHandler.message("password.error",null));
        if(!passwordEncoder.matches(password, user.getPassword())) throw new BadRequestException(messageHandler.message("password.error",null));
        org.springframework.security.core.userdetails.User userAut = (org.springframework.security.core.userdetails.User) authenticationFilter(email, password).getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String access_token = JWT.create()
                .withSubject(userAut.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 600 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role",userAut.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String update_token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        return new UserLoginResponse(user.getEmail(), user.getRole(),  access_token,  update_token);
    }

    @Override
    public void refreshToken(RefreshTokenForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(form.getRefresh_token() == null || !form.getRefresh_token().startsWith("Bearer ")) throw new BadRequestException(messageHandler.message("token.error", null));
        try {
            String refresh_token = form.getRefresh_token().substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refresh_token);
            String email = decodedJWT.getSubject();
            User user = findUserByEmail(email);
            String acceso_token = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 20 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("role", Optional.ofNullable(user.getRole().getAuthority()).stream().collect(Collectors.toList()))
                    .sign(algorithm);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),  new HashMap<>(){{put("message", "the user " + user.getEmail()+ " refresh the token succesfully"); put("access_token", acceso_token); put("update_token", refresh_token);}});
        }catch (Exception exception){
            response.setStatus(FORBIDDEN.value());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), new MessageInfo(exception.getMessage(), 403, request.getRequestURI()));
        }

    }

    @Override
    public String emailUserLoged(HttpServletRequest request) {
        String autorizacionHeader = request.getHeader(AUTHORIZATION);
        if(autorizacionHeader==null || !autorizacionHeader.startsWith("Bearer ")) throw new NotFoundException(messageHandler.message("not.login", null));
        String token = autorizacionHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }
    public User findUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(() -> new NotFoundException(messageHandler.message("not.found", email)));
    }
    @Override
    public User findUserLogedByEmail(HttpServletRequest request) {
        String email = emailUserLoged(request);
        return findUserByEmail(email);
    }
    @Override
    public void isAuthorizate(User user, HttpServletRequest request, Company company){
        User u = findUserLogedByEmail(request);
        if(!user.equals(u) && !company.getUserCompany().equals(u) && !u.getRole().equals(Role.ADMIN))throw new NotFoundException(messageHandler.message("not.authorizate", null));
    }
    @Override
    public void isAuthorizateOnlyUserCreatorAndRolAdmin(User user, HttpServletRequest request){
        User u = findUserLogedByEmail(request);
        if(!user.equals(u) && !u.getRole().equals(Role.ADMIN))throw new NotFoundException(messageHandler.message("not.authorizate", null));
    }

    @Override
    public void contactEmailFormResponse(ContactEmailForm contactEmailForm) {
        emailService.sendEmail(contactEmailForm.getEmail(), "Hello " + contactEmailForm.getName() +"."+ messageHandler.message("contact.success.email", contactEmailForm.getName()), "me, Andrés Rodriguez.-");
    }

    @Override
    public MessageInfo updateUserRol(Long idUser, String roleName, HttpServletRequest request) {
        User user = findUserEntityById(idUser);
        Try.of(() -> {user.setRole(Role.valueOf(roleName)); return userRepository.save(user);
        }).onFailure(e -> {throw new NotFoundException(messageHandler.message("not.found", " the role: " + roleName));});
        return new MessageInfo(messageHandler.message("update.success", "to role: " + roleName), HttpStatus.OK.value(), request.getRequestURL().toString());
    }
}
