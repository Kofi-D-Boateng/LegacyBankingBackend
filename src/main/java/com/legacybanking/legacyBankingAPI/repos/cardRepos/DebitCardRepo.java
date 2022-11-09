package com.legacybanking.legacyBankingAPI.repos.cardRepos;

import com.legacybanking.legacyBankingAPI.models.cards.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@Transactional(readOnly = false)
public interface DebitCardRepo extends JpaRepository<DebitCard,Long> {

    Optional<DebitCard> findByCardNumber(String cardNumber);
}
