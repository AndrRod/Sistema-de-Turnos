package com.turnosRegistro.shift.record.dto.mapper;

import com.turnosRegistro.shift.record.dto.UserDto;
import com.turnosRegistro.shift.record.dto.UserPartDto;
import com.turnosRegistro.shift.record.enums.Role;
import com.turnosRegistro.shift.record.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    @Autowired
    private ReserveMapper reserveMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User entityCreateFromDto(UserDto userDto){
        return new User(
                null,
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getPhoneNumber(),
                userDto.getFirstName(),
                userDto.getLastName(),
                null,
                false,
                Role.CLIENT,
                new HashSet<>());
    }
    public UserDto entityToDto(User user){
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getFirstName(),
                user.getLastName(),
                user.getCreationDate(),
                user.getRole(),
                reserveMapper.listPartDtoFromListEntities(user.getReserveFavorite()));
    }
    public User updateEntityFromDto(User user, UserDto userDto){
        Optional.of(user).ifPresent((u)->{
            if(userDto.getFirstName() != null) u.setFirstName(userDto.getFirstName());
            if(userDto.getLastName() !=null) u.setLastName(userDto.getLastName());
            if(userDto.getEmail() !=null) u.setEmail(userDto.getEmail());
            if(userDto.getPassword() !=null) u.setPassword(passwordEncoder.encode(userDto.getPassword()));
            if(userDto.getPhoneNumber() != null) u.setPhoneNumber(userDto.getPhoneNumber());
        });
        return user;
    }
    public List<Object> ListDtoFromEntities(List<User> users){
        return users.stream().map(this::entityToDto).collect(Collectors.toList());
    }
    public UserPartDto entityToUserPartDto(User user){
        return new UserPartDto(user.getId(), user.getEmail());
    }
}
