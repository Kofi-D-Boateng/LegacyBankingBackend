package com.legacybanking.legacyBankingAPI.models;

import lombok.AllArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
public class LambdaPayload implements Serializable {
    @Serial
    private static final long serialVersionUID = 623326789L;
    private String Function;
    private Object Payload;
}
