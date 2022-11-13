package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.repos.tokenRepo.ConfirmationTokenRepo;
import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import com.legacybanking.legacyBankingAPI.models.securityAndTokens.VerificationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceTest {

    VerificationToken verificationToken;

    @Mock
    ConfirmationTokenRepo tokenRepo;
    @InjectMocks
    ConfirmationTokenService confirmationTokenService;

    @BeforeEach
    void setUp() {
        verificationToken = new VerificationToken("232jej21ej2e2", LocalDateTime.now(),LocalDateTime.now().plusMinutes(16),new Customer());
    }

    @Test
    void retrieveToken() {
        when(tokenRepo.findByToken(verificationToken.getToken())).thenReturn(verificationToken);
        VerificationToken token = confirmationTokenService.retrieveToken(verificationToken.getToken());

        assertEquals(verificationToken.getToken(),token.getToken());
    }

}