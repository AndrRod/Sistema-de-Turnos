package com.turnosRegistro.shift.record.service;

import com.turnosRegistro.shift.record.dto.RefreshTokenForm;
import com.turnosRegistro.shift.record.dto.UserDto;
import com.turnosRegistro.shift.record.dto.UserLoginResponse;
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
    String deleteUserById(Long id);
    MessagePagination findUsersDtoPagination(int page);
    Authentication authenticationFilter(String email, String password) throws AuthenticationException;
    UserLoginResponse userLogin(String email, String password, HttpServletRequest request);
    void refreshToken(RefreshTokenForm form, HttpServletRequest request, HttpServletResponse response) throws IOException;
    User findUserLogedByEmail(HttpServletRequest request);
    String emailUserLoged(HttpServletRequest request);
    String updateUserRol(Long idUser, String roleName);
}
