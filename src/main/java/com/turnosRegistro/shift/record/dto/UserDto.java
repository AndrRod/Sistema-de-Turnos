package com.turnosRegistro.shift.record.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.turnosRegistro.shift.record.enums.Role;
import com.turnosRegistro.shift.record.model.Reserve;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "can´t be empty or null")
    private String email;
    @NotBlank(message = "can´t be empty or null")
    private String password;
    @NotBlank(message = "can´t be empty or null")
    private String phoneNumber;
    private String firstName;
    private String LastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    private Role role;
    private Collection<Reserve> reserveFavorite;
}
