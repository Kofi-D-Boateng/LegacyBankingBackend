package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.enums.*;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Account;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Card;
import com.legacybanking.legacyBankingAPI.models.accounts.CheckingAccount;
import com.legacybanking.legacyBankingAPI.models.accounts.CreditAccount;
import com.legacybanking.legacyBankingAPI.models.cards.CreditCard;
import com.legacybanking.legacyBankingAPI.models.cards.DebitCard;
import com.legacybanking.legacyBankingAPI.models.transaction.*;
import com.legacybanking.legacyBankingAPI.models.transaction.accountTransfer.AccountTransferRequest;
import com.legacybanking.legacyBankingAPI.models.transaction.atmTransaction.ATMTransaction;
import com.legacybanking.legacyBankingAPI.models.transaction.atmTransaction.ATMTransactionRequest;
import com.legacybanking.legacyBankingAPI.models.transaction.vendorTransaction.VendorTransaction;
import com.legacybanking.legacyBankingAPI.models.transaction.vendorTransaction.VendorTransactionRequest;
import com.legacybanking.legacyBankingAPI.repos.*;
import com.legacybanking.legacyBankingAPI.models.bankEntity.Bank;
import com.legacybanking.legacyBankingAPI.models.bankEntity.Branch;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import com.legacybanking.legacyBankingAPI.repos.accountRepos.CheckingAccountRepo;
import com.legacybanking.legacyBankingAPI.repos.accountRepos.CreditAccountRepo;
import com.legacybanking.legacyBankingAPI.repos.bankRepos.BankRepo;
import com.legacybanking.legacyBankingAPI.repos.bankRepos.BranchRepo;
import com.legacybanking.legacyBankingAPI.repos.cardRepos.CreditCardRepo;
import com.legacybanking.legacyBankingAPI.repos.cardRepos.DebitCardRepo;
import com.legacybanking.legacyBankingAPI.repos.transactionRepos.ATMTransactionRepo;
import com.legacybanking.legacyBankingAPI.repos.transactionRepos.AccountTransferRepo;
import com.legacybanking.legacyBankingAPI.repos.transactionRepos.VendorTransactionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    CustomerRepo customerRepo;
    @Mock
    TransactionRepo transactionRepo;
    @Mock
    BranchRepo branchRepo;
    @Mock
    BankRepo bankRepo;
    @Mock
    CreditCardRepo creditCardRepo;
    @Mock
    DebitCardRepo debitCardRepo;
    @Mock
    VendorTransactionRepo vendorTransactionRepo;
    @Mock
    ATMTransactionRepo atmTransactionRepo;
    @Mock
    AccountTransferRepo accountTransferRepo;
    @Mock
    CheckingAccountRepo checkingAccountRepo;
    @Mock
    CreditAccountRepo creditAccountRepo;


    @InjectMocks
    TransactionService transactionService;

    Customer customer;
    Bank bank;

    AccountTransferRequest accountTransaction;
    VendorTransaction vendorTransaction;
    Branch branch;


    private final Date date = new Date();
    private final Timestamp timestamp = new Timestamp(date.getTime());
    private final LocalDateTime currentDate = LocalDateTime.now();
    private static final Double NON_AFFILIATED_TAX = 5.00D;

    @BeforeEach
    void setUp() {
        customer = new Customer("Jon","Doe","Password", LocalDate.now(),"email@email.com"
                ,"The United States","Texas",77777L,"000-00-0000",1000000000L, UserRole.USER,true,2453L);

        branch = new Branch(1L,"Legacy Bank West Branch","The United States",
                "California","63452",new Bank(),500000.25D,73.34D,13.45D);

        List<Branch> list = new ArrayList<>();
        bank =  new Bank(1L,"Legacy Bank","The United States",
                "New York","72704",1000000D,list);
        branch.setBank(bank);
        bank.getBranches().add(branch);
    }

    @Test
    void vendorTransaction() {
        CreditCard creditCard = new CreditCard("12005679832155","031", LocalDateTime.now().plusYears(6),customer,false, CardType.CREDIT, CreditType.PLATINUM);
        CreditAccount creditAccount = new CreditAccount(customer,"","",BankAccountType.CREDIT,1000.00
        ,true,28.0,CreditType.PLATINUM);
        VendorTransactionRequest newRequest = new VendorTransactionRequest(creditCard.getCardNumber(),creditCard.getCardVerificationCode(),customer.getFirstName()+" "+customer.getLastName(),creditCard.getExpirationDate()
        ,creditCard.getType(),"LaLa Pizza","Pizzera/Bar",25.99, TransactionType.PURCHASE,LocalDateTime.now().minusHours(2),"New York");

        VendorTransaction transaction = new VendorTransaction(newRequest.getAmount(), customer,"", newRequest.getLocation(), newRequest.getTransactionType(),newRequest.getDateOfTransaction()
        , newRequest.getMerchantName(), newRequest.getMerchantDescription(), LocalDateTime.now(),creditCard.getType());

        Set<Card> cards = new HashSet<>();
        Set<Account> accounts = new HashSet<>();
        cards.add(creditCard);
        accounts.add(creditAccount);
        creditAccount.setCard(creditCard);

        customer.setCards(cards);
        customer.setAccounts(accounts);

        lenient().when(creditCardRepo.findByCardNumber(newRequest.getCardNumber())).thenReturn(Optional.of(creditCard));

        lenient().when(customerRepo.findByCustomerId(creditCard.getCustomer().getId())).thenReturn(Optional.ofNullable(customer));

        lenient().when(vendorTransactionRepo.save(transaction)).thenReturn(transaction);
        lenient().when(creditAccountRepo.save(creditAccount)).thenReturn(creditAccount);

        boolean isSuccessful = transactionService.vendorTransaction(newRequest);
        assertTrue(isSuccessful);
    }

    @Test
    void atmTransaction() {

        DebitCard debitCard = new DebitCard("1200582714321009","432",LocalDateTime.now().plusYears(4),customer,false,CardType.DEBIT);

        ATMTransactionRequest request = new ATMTransactionRequest(debitCard.getCardNumber(), debitCard.getCardVerificationCode(),LocalDateTime.now()
        ,debitCard.getType(),customer.getAccountPin(),3500.00,TransactionType.DEPOSIT,LocalDateTime.now(),branch.getName());

        ATMTransaction transaction = new ATMTransaction(request.getAmount(),customer,"",request.getLocation(),request.getTransactionType()
        ,request.getDateOfTransaction(),LocalDateTime.now(),request.getType());

        CheckingAccount checkingAccount = new CheckingAccount(customer,"","", BankAccountType.CHECKING
        ,1500.00,true,30.00);

        Set<Card> cards = new HashSet<>();
        Set<Account> accounts = new HashSet<>();

        cards.add(debitCard);
        accounts.add(checkingAccount);
        checkingAccount.setCard(debitCard);
        debitCard.setAccount(checkingAccount);

        customer.setCards(cards);
        customer.setAccounts(accounts);

        Double expected = checkingAccount.getCapital() + request.getAmount();

        when(branchRepo.findByZipcode(request.getLocation())).thenReturn(branch);
        when(bankRepo.getById(branch.getId())).thenReturn(bank);
        when(debitCardRepo.findByCardNumber(request.getCardNumber())).thenReturn(Optional.of(debitCard));
        when(customerRepo.findByCustomerId(debitCard.getId())).thenReturn(Optional.of(customer));
        lenient().when(atmTransactionRepo.save(transaction)).thenReturn(transaction);
        lenient().when(branchRepo.save(branch)).thenReturn(branch);
        lenient().when(bankRepo.save(bank)).thenReturn(bank);
        lenient().when(checkingAccountRepo.save(checkingAccount)).thenReturn(checkingAccount);

        boolean isSuccessful = transactionService.atmTransaction(request);
        assertTrue(isSuccessful);
        assertEquals(expected,checkingAccount.getCapital());
    }

    @Test
    void accountTransfer() {
        CheckingAccount checkingAccount = new CheckingAccount(customer,"12004561234","55002345221", BankAccountType.CHECKING
                ,1500.00,true,30.00);
        DebitCard debitCard = new DebitCard("1200582714321009","432",LocalDateTime.now().plusYears(4),customer,false,CardType.DEBIT);
        AccountTransferRequest request = new AccountTransferRequest(customer.getEmail(),checkingAccount.getAccountNumber(),BankAccountType.CHECKING,"email5@email.com"
        ,null,400.00,TransactionType.TRANSFER,LocalDateTime.now());

        Set<Card> cards = new HashSet<>();
        Set<Account> accounts = new HashSet<>();
        List<Bank> bankList = new ArrayList<>();
        List<Branch> branchList = new ArrayList<>();

        branchList.add(branch);
        bankList.add(bank);
        cards.add(debitCard);
        accounts.add(checkingAccount);
        customer.setCards(cards);
        customer.setAccounts(accounts);

        when(bankRepo.findAll()).thenReturn(bankList);
        when(branchRepo.findAll()).thenReturn(branchList);
        when(customerRepo.findByEmail(request.getEmail())).thenReturn(Optional.of(customer));
        when(customerRepo.findByEmail(request.getEmailOfTransferee())).thenReturn(Optional.empty());

        TransactionNotification notification = transactionService.accountTransfer(request);
        assertFalse(notification.isReceiverInDatabase());
    }
}