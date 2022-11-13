package com.legacybanking.legacyBankingAPI.repos;

import com.legacybanking.legacyBankingAPI.models.abstractClass.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {
}
