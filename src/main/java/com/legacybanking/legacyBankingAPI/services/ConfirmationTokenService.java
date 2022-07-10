package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.Interfaces.ConfirmationTokenInterface;
import com.legacybanking.legacyBankingAPI.Repos.ConfirmationTokenRepo;
import com.legacybanking.legacyBankingAPI.models.VerificationToken;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class ConfirmationTokenService implements ConfirmationTokenInterface {

    @Autowired
    public final ConfirmationTokenRepo tokenRepo;

    @Override
    @Transactional
    public void saveToken(VerificationToken token){
        tokenRepo.save(token);
    }

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
