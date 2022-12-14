package com.legacybanking.legacyBankingAPI.repos.accountRepos;

import com.legacybanking.legacyBankingAPI.models.accounts.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingAccountRepo extends JpaRepository<CheckingAccount, Long> {
}
