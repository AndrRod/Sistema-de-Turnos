package com.turnosRegistro.shift.record.controller;

import com.turnosRegistro.shift.record.dto.userDtos.RefreshTokenForm;
import com.turnosRegistro.shift.record.dto.userDtos.UserDto;
import com.turnosRegistro.shift.record.dto.userDtos.UserLoginForm;
import com.turnosRegistro.shift.record.dto.userDtos.UserLoginResponse;
import com.turnosRegistro.shift.record.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    @Autowired
    private UserService userService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public UserDto registerUser(@RequestBody @Valid UserDto userDto){
        return userService.createUser(userDto);
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public UserLoginResponse loginUser(@RequestBody @Valid UserLoginForm userLogin, HttpServletRequest request){
        return userService.userLogin(userLogin.getEmail(), userLogin.getPassword(), request);
    }
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public void refreshToken(@RequestBody RefreshTokenForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(form, request, response);
    }
}