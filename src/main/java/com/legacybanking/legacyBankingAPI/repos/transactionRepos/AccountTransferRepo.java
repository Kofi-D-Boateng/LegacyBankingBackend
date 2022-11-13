package com.legacybanking.legacyBankingAPI.repos.transactionRepos;

import com.legacybanking.legacyBankingAPI.models.transaction.accountTransfer.AccountTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = false)
public interface AccountTransferRepo extends JpaRepository<AccountTransfer,Long> {
}
