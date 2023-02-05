package com.legacybanking.legacyBankingAPI.models.cards;

import com.legacybanking.legacyBankingAPI.models.abstractClass.Card;
import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "debit_card")
public class DebitCard extends Card {
    public DebitCard(String cardNumber, String cardVerificationCode, LocalDateTime expirationDate, Customer customer, Boolean isLocked, CardType type) {
        super(cardNumber, cardVerificationCode, expirationDate, customer, isLocked, type);
    }
}
