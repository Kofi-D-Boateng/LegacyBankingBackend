package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.models.transaction.*;
import com.legacybanking.legacyBankingAPI.models.transaction.accountTransfer.AccountTransferRequest;
import com.legacybanking.legacyBankingAPI.models.transaction.atmTransaction.ATMTransaction;
import com.legacybanking.legacyBankingAPI.models.transaction.vendorTransaction.VendorTransaction;
import com.legacybanking.legacyBankingAPI.repos.*;
import com.legacybanking.legacyBankingAPI.models.bankEntity.Bank;
import com.legacybanking.legacyBankingAPI.models.bankEntity.Branch;
import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import com.legacybanking.legacyBankingAPI.repos.bankRepos.BankRepo;
import com.legacybanking.legacyBankingAPI.repos.bankRepos.BranchRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    @InjectMocks
    TransactionService transactionService;

    Customer customer;
    Bank bank;
    com.legacybanking.legacyBankingAPI.models.transaction.atmTransaction.ATMTransaction ATMTransaction;
    AccountTransferRequest accountTransaction;
    VendorTransaction vendorTransaction;
    Branch branch;

    private static final String DEPOSIT = "deposit";
    private static final String WITHDRAWAL = "withdrawal";
    private static final String ACHDEBIT = "ACH Debit";
    private static final String ACHCREDIT = "ACH Credit";
    private static final String TRANSFER = "Debit transfer";
    private static final String CREDIT = "Credit";
    private static final String DEBIT = "Debit";
    private final Date date = new Date();
    private final Timestamp timestamp = new Timestamp(date.getTime());
    private final LocalDateTime currentDate = LocalDateTime.now();

    @BeforeEach
    void setUp() {
//        customer = new Customer(1L,"Jon","Doe","Password",LocalDate.now(),"email@email.com"
//                ,"The United States","Texas",76012L,"000-00-0000",50000.00D,1000000000L,"120034198",
//                "053351821",123453920185762L,134,false,
//                true, CustomerRole.USER,new ArrayList<Transaction>());

//        ATMTransaction = new ATMTransfer(1000.63D,123453920185762L,312,"","",TransactionType.DEPOSIT,LocalDate.now(),2891L);

        ATMTransaction = new ATMTransaction();

        accountTransaction = new AccountTransferRequest();

//        vendorTransaction = new VendorTransaction(1000.63D,123453920185762L,352,"","",TransactionType.PURCHASE,CardType.DEBIT,LocalDate.now()
//        ,LocalDate.now(),false,false);

        vendorTransaction = new VendorTransaction();

        branch = new Branch(1L,"Legacy Bank West Branch","The United States",
                "California","63452",new Bank(),500000.25D,73.34D,13.45D);
        List<Branch> list = new ArrayList<>();

        list.add(branch);

        bank =  new Bank(1L,"Legacy Bank","The United States",
                "New York","72704",1000000D,list);
    }

    @Test
    void vendorTransaction() {
//        when(customerRepo.findByCardNumber(vendorTransaction.getCardNumber())).thenReturn(customer);
//        boolean isSuccessful = transactionService.vendorTransaction(vendorTransaction);
//        assertTrue(isSuccessful);
    }

    @Test
    void atmTransaction() {
//        when(branchRepo.findByZipcode(ATMTransaction.getLocation())).thenReturn(branch);
//        when(bankRepo.getById(branch.getBank().getId())).thenReturn(bank);
//        when(customerRepo.findByCardNumber(ATMTransaction.getCardNumber())).thenReturn(customer);
//
//        boolean isSuccessful = transactionService.atmTransaction(ATMTransaction);
//        assertTrue(isSuccessful);
    }

    @Test
    void accountTransfer() {
        List<Bank> bankList = new ArrayList<>();
        List<Branch> branchList = new ArrayList<>();
        branchList.add(branch);
        bankList.add(bank);
        when(bankRepo.findAll()).thenReturn(bankList);
        when(branchRepo.findAll()).thenReturn(branchList);
//        when(customerRepo.findByAccountNumber(accountTransaction.getAccountNumber())).thenReturn(customer);

        TransactionNotification notification = transactionService.accountTransfer(accountTransaction);
        assertFalse(notification.isReceiverInDatabase());
    }
}