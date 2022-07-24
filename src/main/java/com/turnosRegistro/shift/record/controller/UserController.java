package com.turnosRegistro.shift.record.controller;

import com.turnosRegistro.shift.record.formsAndResponses.AddRoleToUserForm;
import com.turnosRegistro.shift.record.dto.UserDto;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import com.turnosRegistro.shift.record.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
@CrossOrigin(origins = {"http://127.0.0.1:3000"}
        , methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE}
        ,allowCredentials = "true")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id){
        return userService.findUserDtoById(id);
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public MessageInfo deleteById(@PathVariable Long id, HttpServletRequest request){
        return userService.deleteUserById(id, request);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public UserDto updateById(@PathVariable Long id, @RequestBody @Valid UserDto userDto, HttpServletRequest request){
        return userService.updateUser(id, userDto, request);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public MessagePagination usersPaginationByPage(@RequestParam String page, HttpServletRequest request){
        return userService.findUsersDtoPagination(Integer.parseInt(page), request);
    }
    @PutMapping("/role/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageInfo userUpdateRole(@PathVariable String id, @RequestBody AddRoleToUserForm role, HttpServletRequest request){
        return userService.updateUserRol(Long.valueOf(id), role.getRoleName(), request);
    }
}
