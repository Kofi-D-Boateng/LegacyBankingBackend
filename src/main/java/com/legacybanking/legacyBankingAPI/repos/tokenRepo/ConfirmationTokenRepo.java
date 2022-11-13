package com.legacybanking.legacyBankingAPI.repos.tokenRepo;

import com.legacybanking.legacyBankingAPI.models.securityAndTokens.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ConfirmationTokenRepo extends JpaRepository<VerificationToken, Long> {
    @Query("select t from VerificationToken t where t.token = ?1")
    VerificationToken findByToken(String token);
}