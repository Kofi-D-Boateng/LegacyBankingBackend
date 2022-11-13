package com.legacybanking.legacyBankingAPI.repos.accountRepos;

import com.legacybanking.legacyBankingAPI.models.accounts.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckingAccountRepo extends JpaRepository<CheckingAccount, Long> {
}
