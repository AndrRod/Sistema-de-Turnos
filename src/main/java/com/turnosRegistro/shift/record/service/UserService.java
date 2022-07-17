package com.turnosRegistro.shift.record.service;

import com.turnosRegistro.shift.record.authFormsAndResponses.RefreshTokenForm;
import com.turnosRegistro.shift.record.dto.UserDto;
import com.turnosRegistro.shift.record.authFormsAndResponses.UserLoginResponse;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.exception.MessagePagination;
import com.turnosRegistro.shift.record.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(Long idUser, UserDto userDto);
    User findUserEntityById(Long id);
    UserDto findUserDtoById(Long id);
    MessageInfo deleteUserById(Long id, HttpServletRequest request);
    MessagePagination findUsersDtoPagination(int page, HttpServletRequest request);
    Authentication authenticationFilter(String email, String password) throws AuthenticationException;
    UserLoginResponse userLogin(String email, String password, HttpServletRequest request);
    void refreshToken(RefreshTokenForm form, HttpServletRequest request, HttpServletResponse response) throws IOException;
    User findUserLogedByEmail(HttpServletRequest request);
    String emailUserLoged(HttpServletRequest request);
    MessageInfo updateUserRol(Long idUser, String roleName, HttpServletRequest request);
    void isAuthorizate(User users, HttpServletRequest request);
}
