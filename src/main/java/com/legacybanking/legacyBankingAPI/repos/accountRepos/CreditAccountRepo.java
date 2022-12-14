package com.legacybanking.legacyBankingAPI.repos.accountRepos;

import com.legacybanking.legacyBankingAPI.models.accounts.CreditAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditAccountRepo extends JpaRepository<CreditAccount,Long> {
}
