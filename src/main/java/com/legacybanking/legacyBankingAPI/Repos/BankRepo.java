package com.legacybanking.legacyBankingAPI.Repos;
import com.legacybanking.legacyBankingAPI.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public interface BankRepo extends JpaRepository<Bank, Long> {
}

