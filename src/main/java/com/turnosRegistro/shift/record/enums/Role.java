package com.turnosRegistro.shift.record.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CLIENT(Code.CLIENT),
    COMPANY(Code.COMPANY),
    ADMIN(Code.ADMIN);

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return authority;
    }

    public class Code {
        public static final String CLIENT = "ROLE_CLIENT";
        public static final String COMPANY = "ROLE_COMPANY";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}