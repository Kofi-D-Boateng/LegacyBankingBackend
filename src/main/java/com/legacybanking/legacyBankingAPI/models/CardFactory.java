package com.legacybanking.legacyBankingAPI.models;

import com.legacybanking.legacyBankingAPI.models.abstractClass.Card;
import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.enums.CreditCardType;
import com.legacybanking.legacyBankingAPI.models.cards.CreditCard;
import com.legacybanking.legacyBankingAPI.models.cards.DebitCard;
import com.legacybanking.legacyBankingAPI.models.customer.Customer;

import java.time.LocalDateTime;
import java.util.Random;


public class CardFactory {
    public Card createCard(CardType type, CreditCardType creditCardType, Customer customer){
        LocalDateTime dateTime = LocalDateTime.now();
        if(type == null) return null;

        else if(type.equals(CardType.DEBIT)) {
            StringBuilder stringCardNumber = new StringBuilder(), stringCvc = new StringBuilder();
            Long cardNumber = (long)(Math.random()*(99999999L * 10000000 +1)+ 10000);
            Integer cvc = new Random().nextInt(1000);
            stringCardNumber.append(cardNumber);
            stringCvc.append(cvc);
            LocalDateTime expirationDate = dateTime.plusYears(6).plusMonths(3);
            return new DebitCard(stringCardNumber.toString(),stringCvc.toString(),expirationDate,customer,true,type);
        }

        else if(type.equals(CardType.CREDIT)) {
            StringBuilder stringCardNumber = new StringBuilder(), stringCvc = new StringBuilder();
            Long cardNumber = (long)(Math.random()*(99999999L * 10000000 +1)+ 10000);
            Integer cvc = new Random().nextInt(1000);
            stringCardNumber.append(cardNumber);
            stringCvc.append(cvc);
            LocalDateTime expirationDate = dateTime.plusYears(6).plusMonths(3);
            return new CreditCard(stringCardNumber.toString(),stringCvc.toString(),expirationDate,customer,true,type,creditCardType);
        }

        return null;
    }
}
