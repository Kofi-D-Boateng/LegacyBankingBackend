package com.legacybanking.legacyBankingAPI.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class LoginRequest {
    private Long id;
    private final String email;
    private final String password;

    public String getUsername() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
