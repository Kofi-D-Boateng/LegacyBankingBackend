package com.legacybanking.legacyBankingAPI.repos.bankRepos;
import com.legacybanking.legacyBankingAPI.models.bankEntity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public interface BankRepo extends JpaRepository<Bank, Long> {
}

