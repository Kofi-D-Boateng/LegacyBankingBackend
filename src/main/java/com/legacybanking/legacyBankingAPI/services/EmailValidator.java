package com.legacybanking.legacyBankingAPI.services;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
//        VALIDATOR LATER
        return true;
    }
}