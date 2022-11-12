package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.enums.BankAccountType;
import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Account;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Card;
import com.legacybanking.legacyBankingAPI.models.accounts.CheckingAccount;
import com.legacybanking.legacyBankingAPI.models.accounts.CreditAccount;
import com.legacybanking.legacyBankingAPI.models.accounts.SavingsAccount;
import com.legacybanking.legacyBankingAPI.models.cards.CreditCard;
import com.legacybanking.legacyBankingAPI.models.cards.DebitCard;
import com.legacybanking.legacyBankingAPI.repos.CustomerRepo;
import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import com.legacybanking.legacyBankingAPI.models.securityAndTokens.SecurityModel;
import com.legacybanking.legacyBankingAPI.repos.accountRepos.CheckingAccountRepo;
import com.legacybanking.legacyBankingAPI.repos.accountRepos.CreditAccountRepo;
import com.legacybanking.legacyBankingAPI.repos.accountRepos.SavingsAccountRepo;
import com.legacybanking.legacyBankingAPI.repos.cardRepos.CreditCardRepo;
import com.legacybanking.legacyBankingAPI.repos.cardRepos.DebitCardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class SecurityService {

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CreditAccountRepo creditAccountRepo;
    @Autowired
    private CheckingAccountRepo checkingAccountRepo;
    @Autowired
    private SavingsAccountRepo savingsAccountRepo;
    @Autowired
    private CreditCardRepo creditCardRepo;
    @Autowired
    private DebitCardRepo debitCardRepo;


    public boolean setSecurity(SecurityModel security){
        Optional<Customer> customer = customerRepo.findByEmail(security.getEmail());

        if(customer.isEmpty()){
            return false;
        }
        if(Objects.equals(security.getRequestType(), "LOCK ACCOUNT")){
            Account accountToBeLocked = customer.get().getAccounts().stream().parallel().filter(account -> account.getAccountNumber().equals(security.getAccountNumber()))
                    .reduce((a,b)->{throw new IllegalStateException("Multiple elements: " + a + ", " + b);}).get();
            accountToBeLocked.setIsEnabled(false);
            if(accountToBeLocked.getBankAccountType().equals(BankAccountType.CHECKING)){
                CheckingAccount savingEntity = (CheckingAccount) accountToBeLocked;
                checkingAccountRepo.save(savingEntity);
            }else if(accountToBeLocked.getBankAccountType().equals(BankAccountType.CREDIT)){
                CreditAccount savingEntity = (CreditAccount) accountToBeLocked;
                creditAccountRepo.save(savingEntity);
            }else if(accountToBeLocked.getBankAccountType().equals(BankAccountType.SAVINGS)){
                SavingsAccount savingEntity = (SavingsAccount) accountToBeLocked;
                savingsAccountRepo.save(savingEntity);
            }
            return true;
        }
        else if(Objects.equals(security.getRequestType(), "LOCK CARD")){
            Card cardToBeLocked = customer.get().getCards().stream().parallel().filter(card -> card.getCardNumber().equals(security.getCardNumber()))
                    .reduce((a,b)->{throw new IllegalStateException("Multiple elements: " + a + ", " + b);}).get();

            cardToBeLocked.setIsLocked(true);
            if(cardToBeLocked.getType().equals(CardType.DEBIT)){
                DebitCard savingEntity = (DebitCard) cardToBeLocked;
                debitCardRepo.save(savingEntity);
            }else{
                CreditCard savingEntity = (CreditCard) cardToBeLocked;
                creditCardRepo.save(savingEntity);
            }
            return true;
        }
       return false;
    }
}
