package com.turnosRegistro.shift.record.formsAndResponses;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddRoleToUserForm {
    @NotBlank(message = "can't be null or empty")
    private String roleName;
}
