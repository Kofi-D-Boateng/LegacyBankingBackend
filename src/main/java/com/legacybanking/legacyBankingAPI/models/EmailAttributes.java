package com.legacybanking.legacyBankingAPI.models;

import lombok.AllArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
public class EmailAttributes implements Serializable {
    @Serial
    private static final long serialVersionUID = 733326789L;
    private String token;
    private String name;
    private String email;
}
