package com.turnosRegistro.shift.record.controller;

import com.turnosRegistro.shift.record.dto.userDtos.AddRoleToUserForm;
import com.turnosRegistro.shift.record.dto.userDtos.UserDto;
import com.turnosRegistro.shift.record.exception.MessageInfo;
import com.turnosRegistro.shift.record.exception.MessagePagination;
import com.turnosRegistro.shift.record.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    @PutMapping("/{idUser}")
    public UserDto updateById(@PathVariable Long idUser, @RequestBody @Valid UserDto userDto){
        return userService.updateUser(idUser, userDto);
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
