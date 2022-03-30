package com.legacybanking.legacyBankingAPI.Repos;

import com.legacybanking.legacyBankingAPI.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
