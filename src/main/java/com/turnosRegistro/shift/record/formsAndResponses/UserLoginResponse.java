package com.turnosRegistro.shift.record.formsAndResponses;
import com.turnosRegistro.shift.record.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserLoginResponse {
    private String email;
    private Role role;
    private String accessToken;
    private String refreshToken;
}
