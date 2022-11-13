package com.legacybanking.legacyBankingAPI.repos.accountRepos;

import com.legacybanking.legacyBankingAPI.models.accounts.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsAccountRepo extends JpaRepository<SavingsAccount,Long> {
}
