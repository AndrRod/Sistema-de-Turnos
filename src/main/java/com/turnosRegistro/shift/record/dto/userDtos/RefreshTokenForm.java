package com.turnosRegistro.shift.record.dto.userDtos;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RefreshTokenForm {
    @NotBlank(message = "can't be null or empty")
    private String refresh_token;
}
