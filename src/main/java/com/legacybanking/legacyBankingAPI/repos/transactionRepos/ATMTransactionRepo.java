package com.legacybanking.legacyBankingAPI.repos.transactionRepos;

import com.legacybanking.legacyBankingAPI.models.transaction.atmTransaction.ATMTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = false)
public interface ATMTransactionRepo extends JpaRepository<ATMTransaction,Long> {
}
