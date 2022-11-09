package com.legacybanking.legacyBankingAPI.models.cards;

import com.legacybanking.legacyBankingAPI.models.abstractClass.Card;
import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.enums.CreditCardType;
import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit_card")
public class CreditCard extends Card {
    @Column(name = "credit_card_type",
    nullable = false)
    private CreditCardType creditCardType;
    public CreditCard(String cardNumber, String cardVerificationCode, LocalDateTime expirationDate, Customer customer, Boolean isLocked, CardType type, CreditCardType creditCardType) {
        super(cardNumber, cardVerificationCode, expirationDate, customer, isLocked, type);
        this.creditCardType = creditCardType;
    }
}
