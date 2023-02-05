package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.enums.CreditType;
import com.legacybanking.legacyBankingAPI.enums.Department;
import com.legacybanking.legacyBankingAPI.enums.UserRole;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Card;
import com.legacybanking.legacyBankingAPI.models.cards.CreditCard;
import com.legacybanking.legacyBankingAPI.repos.CustomerRepo;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import com.legacybanking.legacyBankingAPI.models.securityAndTokens.SecurityModel;
import com.legacybanking.legacyBankingAPI.repos.cardRepos.CreditCardRepo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {

    SecurityModel securityModel;
    Customer customer;

    @Mock
    CustomerRepo customerRepo;
    @Mock
    CreditCardRepo creditCardRepo;

    @InjectMocks
    SecurityService securityService;


    @Test
    void setSecurity() {
        CreditCard creditCard = new CreditCard("12005679832155","031", LocalDateTime.now(),customer,false, CardType.CREDIT, CreditType.PLATINUM);
        customer = new Customer("Jon","Doe","Password", LocalDate.now(),"email@email.com"
                ,"The United States","Texas",77777L,"000-00-0000",1000000000L, UserRole.CUSTOMER, Department.NONE,false,2453L);
        securityModel = new SecurityModel("","",creditCard.getCardNumber(),"LOCK CARD", customer.getEmail());
        Set<Card> set = new HashSet<>();
        set.add(creditCard);
        customer.setCards(set);

        when(customerRepo.findByEmail(securityModel.getEmail())).thenReturn(Optional.of(customer));
        when(creditCardRepo.save(creditCard)).thenReturn(creditCard);

        boolean check = securityService.setSecurity(securityModel);
        assertTrue(check);
        assertTrue(creditCard.getIsLocked());
    }
}