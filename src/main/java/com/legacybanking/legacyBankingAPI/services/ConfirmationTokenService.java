package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.Interfaces.ConfirmationTokenInterface;
import com.legacybanking.legacyBankingAPI.Repos.ConfirmationTokenRepo;
import com.legacybanking.legacyBankingAPI.models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ConfirmationTokenService implements ConfirmationTokenInterface {

    @Autowired
    public ConfirmationTokenRepo tokenRepo;



    @Override
    public VerificationToken retrieveToken(String token){
        return tokenRepo.findByToken(token);
    }

    @Override
    @Transactional
    public void removeTokenFromDB(VerificationToken token) {
        tokenRepo.delete(token);
    }
}
