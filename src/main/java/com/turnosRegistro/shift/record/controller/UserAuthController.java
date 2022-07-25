package com.turnosRegistro.shift.record.controller;

import com.turnosRegistro.shift.record.formsAndResponses.ContactEmailForm;
import com.turnosRegistro.shift.record.formsAndResponses.RefreshTokenForm;
import com.turnosRegistro.shift.record.dto.UserDto;
import com.turnosRegistro.shift.record.formsAndResponses.UserLoginForm;
import com.turnosRegistro.shift.record.formsAndResponses.UserLoginResponse;
import com.turnosRegistro.shift.record.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin(origins = {"http://127.0.0.1:3000"}
        , methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE}
        ,allowCredentials = "true")
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
    @PostMapping("/contact")
    @ResponseStatus(HttpStatus.OK)
    public void refreshToken(@RequestBody ContactEmailForm contactEmailForm) {
        userService.contactEmailFormResponse(contactEmailForm);
    }
}