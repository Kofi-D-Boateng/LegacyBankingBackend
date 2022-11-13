package com.legacybanking.legacyBankingAPI.repos;

import com.legacybanking.legacyBankingAPI.models.abstractClass.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
