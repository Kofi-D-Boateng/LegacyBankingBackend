package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.enums.BankAccountType;
import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.enums.TransactionType;
import com.legacybanking.legacyBankingAPI.models.accounts.CheckingAccount;
import com.legacybanking.legacyBankingAPI.models.accounts.CreditAccount;
import com.legacybanking.legacyBankingAPI.models.cards.CreditCard;
import com.legacybanking.legacyBankingAPI.models.cards.DebitCard;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import com.legacybanking.legacyBankingAPI.models.transaction.*;
import com.legacybanking.legacyBankingAPI.models.transaction.accountTransfer.AccountTransfer;
import com.legacybanking.legacyBankingAPI.models.transaction.accountTransfer.AccountTransferRequest;
import com.legacybanking.legacyBankingAPI.models.transaction.atmTransaction.ATMTransaction;
import com.legacybanking.legacyBankingAPI.models.transaction.atmTransaction.ATMTransactionRequest;
import com.legacybanking.legacyBankingAPI.models.transaction.vendorTransaction.VendorTransaction;
import com.legacybanking.legacyBankingAPI.models.transaction.vendorTransaction.VendorTransactionRequest;
import com.legacybanking.legacyBankingAPI.repos.accountRepos.CheckingAccountRepo;
import com.legacybanking.legacyBankingAPI.repos.accountRepos.CreditAccountRepo;
import com.legacybanking.legacyBankingAPI.repos.accountRepos.SavingsAccountRepo;
import com.legacybanking.legacyBankingAPI.repos.bankRepos.BankRepo;
import com.legacybanking.legacyBankingAPI.repos.bankRepos.BranchRepo;
import com.legacybanking.legacyBankingAPI.repos.CustomerRepo;
import com.legacybanking.legacyBankingAPI.repos.TransactionRepo;
import com.legacybanking.legacyBankingAPI.models.bankEntity.Bank;
import com.legacybanking.legacyBankingAPI.models.bankEntity.Branch;
import com.legacybanking.legacyBankingAPI.repos.cardRepos.CreditCardRepo;
import com.legacybanking.legacyBankingAPI.repos.cardRepos.DebitCardRepo;
import com.legacybanking.legacyBankingAPI.repos.transactionRepos.ATMTransactionRepo;
import com.legacybanking.legacyBankingAPI.repos.transactionRepos.AccountTransferRepo;
import com.legacybanking.legacyBankingAPI.repos.transactionRepos.VendorTransactionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
@Transactional
@Slf4j
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private BranchRepo branchRepo;
    @Autowired
    private BankRepo bankRepo;
    @Autowired
    private CreditCardRepo creditCardRepo;
    @Autowired
    private DebitCardRepo debitCardRepo;
    @Autowired
    private VendorTransactionRepo vendorTransactionRepo;
    @Autowired
    private AccountTransferRepo accountTransferRepo;
    @Autowired
    private ATMTransactionRepo atmTransactionRepo;
    @Autowired
    CheckingAccountRepo checkingAccountRepo;
    @Autowired
    CreditAccountRepo creditAccountRepo;
    @Autowired
    SavingsAccountRepo savingsAccountRepo;


    private final Date date = new Date();
    private final Timestamp timestamp = new Timestamp(date.getTime());
    private final LocalDateTime currentDate = LocalDateTime.now();

    private static final Double NON_AFFILIATED_TAX = 5.00D;





    @Transactional
    public boolean vendorTransaction(VendorTransactionRequest request){



        if(request.getType().equals(CardType.CREDIT)){

            Optional<CreditCard> foundCard = creditCardRepo.findByCardNumber(request.getCardNumber());
            if(foundCard.isEmpty()){
                return false;
            }else{
                Optional<Customer> customer = customerRepo.findByCustomerId(foundCard.get().getCustomer().getId());

                if(customer.isEmpty()){
                    return false;
                }

                CreditAccount customerAccount = (CreditAccount) customer.get().getAccounts().stream().parallel().filter(account -> account.getCard().equals(foundCard.get())).reduce((a,b)->{throw new IllegalStateException("Multiple elements: " + a + ", " + b);}).get();




                log.info("TRANSACTION: {}", request);

                if(!customer.get().getIsActivated()){
                    log.info("Faulty transaction attempted at this time:{} at location:{}",timestamp,request.getLocation());
                    return false;
                }

                if(request.getExpirationDate().isBefore(currentDate)){
                    log.info("Faulty transaction attempted at this time:{} at location:{}",timestamp,request.getLocation());
                    return false;
                }

                if(request.getTransactionType().equals(TransactionType.REFUND)){
                    if(customerAccount.deposit(request.getAmount())) {
                        VendorTransaction transaction = new VendorTransaction(request.getAmount(),customer.get(),
                                customerAccount.getAccountNumber(), request.getLocation(),
                                request.getTransactionType(),request.getDateOfTransaction(),request.getMerchantName(), request.getMerchantDescription(),currentDate,request.getType());

                        vendorTransactionRepo.save(transaction);
                        creditAccountRepo.save(customerAccount);
                        return true;
                    }else{
                        return false;
                    }
                }else if(request.getTransactionType().equals(TransactionType.PURCHASE)){

                    if(!customerAccount.withdraw(request.getAmount())) {
                        log.info("Faulty transaction attempted at this time:{} at location:{}", timestamp, request.getLocation());
                        return false;
                    }
                    VendorTransaction transaction = new VendorTransaction(request.getAmount(),customer.get(),
                            customerAccount.getAccountNumber(), request.getLocation(),
                            request.getTransactionType(),request.getDateOfTransaction(),request.getMerchantName(), request.getMerchantDescription(),currentDate,request.getType());

                    vendorTransactionRepo.save(transaction);
                    creditAccountRepo.save(customerAccount);
                    return true;
                }
            }
            return false;
        }





        else if(request.getType().equals(CardType.DEBIT)){
            Optional<DebitCard> foundCard = debitCardRepo.findByCardNumber(request.getCardNumber());
            if(foundCard.isEmpty()){
                return false;
            }else{
                Optional<Customer> customer = customerRepo.findByCustomerId(foundCard.get().getCustomer().getId());

                if(customer.isEmpty()){
                    return false;
                }

                CheckingAccount customerAccount = (CheckingAccount) customer.get().getAccounts().stream().parallel().filter(account -> account.getCard().equals(foundCard.get()));

                log.info("TRANSACTION: {}", request);

                if(!customer.get().getIsActivated()){
                    log.info("Faulty transaction attempted at this time:{} at location:{}",timestamp,request.getLocation());
                    return false;
                }

                if(request.getExpirationDate().isBefore(currentDate)){
                    log.info("Faulty transaction attempted at this time:{} at location:{}",timestamp,request.getLocation());
                    return false;
                }

                if(request.getTransactionType().equals(TransactionType.REFUND)){
                    if(customerAccount.deposit(request.getAmount())){
                        VendorTransaction transaction = new VendorTransaction(request.getAmount(),customer.get(),
                                customerAccount.getAccountNumber(), request.getLocation(),
                                request.getTransactionType(),request.getDateOfTransaction(),request.getMerchantName(), request.getMerchantDescription(),currentDate,request.getType());

                        vendorTransactionRepo.save(transaction);
                        checkingAccountRepo.save(customerAccount);
                        return true;
                    }else{
                        return false;
                    }
                }else if(request.getTransactionType().equals(TransactionType.PURCHASE)){
                    if(customerAccount.withdraw(request.getAmount())) {
                        VendorTransaction transaction = new VendorTransaction(request.getAmount(),customer.get(),
                                customerAccount.getAccountNumber(), request.getLocation(),
                                request.getTransactionType(),request.getDateOfTransaction(),request.getMerchantName(), request.getMerchantDescription(),currentDate,request.getType());

                        vendorTransactionRepo.save(transaction);
                        checkingAccountRepo.save(customerAccount);
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Transactional
    public boolean atmTransaction(ATMTransactionRequest request){
        Branch branch = branchRepo.findByZipcode(request.getLocation());
        Bank bank = bankRepo.getById(branch.getBank().getId());
        Optional<DebitCard> optionalCard = debitCardRepo.findByCardNumber(request.getCardNumber());

        if(optionalCard.isEmpty()){
            return false;
        }

        Optional<Customer> customer = customerRepo.findByCustomerId(optionalCard.get().getCustomer().getId());

        if(customer.isEmpty()){
            return false;
        }


        log.info("BRANCH:{}", branch);
        log.info("BANK:{}", bank);
        log.info("TRANSACTION: {}", request);


        if(request.getAmount() <=0 || request.getAmount().isNaN()){
            return false;
        }
        if(branch.getBranchHoldings() - request.getAmount() <= 0){
            log.info("Branch:{} is currently low on funds., timestamp:{}",
                    branch.getName(), timestamp);
            return false;
        }
        if(branch.getBranchHoldings() <= 0){
            log.info("[ERROR]: Low branch funds. Current holdings are: {}, timestamp:{}",
                    bank.getTotalHoldings(), timestamp);
            return false;
        }

        if(!Objects.equals(request.getAccountPin(), customer.get().getAccountPin())){
            log.info("Faulty transaction attempted at this time:{} at location:{}",timestamp,request.getLocation());
            return false;
        }

        if(!customer.get().getIsActivated()){
            log.info("Faulty transaction attempted at this time:{} at location:{}",timestamp,request.getLocation());
            return false;
        }


        CheckingAccount account = (CheckingAccount) customer.get().getAccounts().stream().parallel().filter(account1 ->
                account1.getCard().equals(optionalCard.get())).reduce((a,b)->{throw new IllegalStateException("Multiple elements: " + a + ", " + b);}).get();

        if(branch.getName().trim().length() > 0){
            if(request.getTransactionType().equals(TransactionType.WITHDRAW)){
                if(!account.withdraw(request.getAmount())){
                    log.info("CURRENT USER_ID:{} funds '{}' are too low for transaction",customer.get().getId(),account.getCapital());
                    return false;
                }
                request.setLocation(branch.getName());
                branch.setBranchHoldings(branch.getBranchHoldings() - request.getAmount());
                bank.setTotalHoldings(bank.getTotalHoldings() - request.getAmount());

                ATMTransaction transaction = new ATMTransaction(request.getAmount(), customer.get(), account.getAccountNumber(), request.getLocation(),
                        request.getTransactionType(),request.getDateOfTransaction(),currentDate,request.getType());

                atmTransactionRepo.save(transaction);
                branchRepo.save(branch);
                bankRepo.save(bank);
                checkingAccountRepo.save(account);
                return true;
            }
            else if(request.getTransactionType().equals(TransactionType.DEPOSIT)){
                request.setLocation(branch.getName());
                branch.setBranchHoldings(branch.getBranchHoldings() + request.getAmount());
                bank.setTotalHoldings(bank.getTotalHoldings() + request.getAmount());
                account.deposit(request.getAmount());

                ATMTransaction transaction = new ATMTransaction(request.getAmount(), customer.get(), account.getAccountNumber(), request.getLocation(),
                        request.getTransactionType(),request.getDateOfTransaction(),currentDate,request.getType());

                atmTransactionRepo.save(transaction);
                branchRepo.save(branch);
                bankRepo.save(bank);
                checkingAccountRepo.save(account);

                return true;
            }
        }else{
            if(request.getTransactionType().equals(TransactionType.WITHDRAW)){
                Double taxedAmount = (request.getAmount() * (NON_AFFILIATED_TAX/100.00D));
                if(!account.deposit(taxedAmount)){
                    log.info("CURRENT USER_ID:{} funds '{}' are too low for transaction",customer.get().getId(),account.getCapital());
                    return false;
                }
                request.setLocation(request.getLocation());
                bank.setTotalHoldings(bank.getTotalHoldings() - request.getAmount() + (request.getAmount() * (NON_AFFILIATED_TAX / 100.00D)));

                ATMTransaction transaction = new ATMTransaction(request.getAmount(), customer.get(), account.getAccountNumber(), request.getLocation(),
                        request.getTransactionType(),request.getDateOfTransaction(),currentDate,request.getType());


                bank.getBranches().stream().parallel().forEach(branch1->{
                    branch.setBranchHoldings(bank.getTotalHoldings()/bank.getBranches().size());
                    branchRepo.save(branch1);
                });

                atmTransactionRepo.save(transaction);
                bankRepo.save(bank);
                checkingAccountRepo.save(account);

                return true;
            }
            log.info("Faulty transaction attempted at this location:{}",request.getLocation());
            return false;
        }
        log.info("Faulty transaction attempted at this location:{}",request.getLocation());
        return false;
    }

    @Transactional
    public TransactionNotification accountTransfer(AccountTransferRequest request){
        List<Bank> bank = bankRepo.findAll();
        List<Branch> branches = branchRepo.findAll();
        Instant instant = Instant.ofEpochMilli(request.getDateOfTransaction());

        Optional<Customer> optionalCustomer = customerRepo.findByEmail(request.getEmail());

        if(optionalCustomer.isEmpty()){
            return null;
        }

        Customer customer = optionalCustomer.get();

        if(!customer.getIsActivated()){
            log.info("Faulty transaction attempted at this time:{}",timestamp);
            return null;
        }

        CheckingAccount customerAccount = (CheckingAccount) customer.getAccounts().stream()
                .parallel()
                .filter(account1 -> account1.getAccountNumber().equals(request.getAccountNumber()))
                .findFirst()
                .orElse(null);

        if(customerAccount == null){
            return null;
        }

        if(request.getTransactionType().equals(TransactionType.TRANSFER)){


            Optional<Customer> transferee = request.getPhoneNumberOfTransferee() != null
                    ? customerRepo.findTransfereeByPhoneNumber(request.getPhoneNumberOfTransferee())
                    : customerRepo.findByEmail(request.getEmailOfTransferee());

            if((customerAccount.getCapital()- request.getAmount())< customerAccount.getMinimumBalance()){
                return null;
            }

            if(transferee.isEmpty()){
                if(!customerAccount.withdraw(request.getAmount())){
                    return new TransactionNotification();
                }

                bank.get(0).setTotalHoldings(bank.get(0).getTotalHoldings()-request.getAmount());
                BigDecimal bankHoldings = BigDecimal.valueOf(bank.get(0).getTotalHoldings());
                Double newBranchHoldings = bankHoldings.multiply(BigDecimal.valueOf(100)).divide(new BigDecimal(branches.size()),2, RoundingMode.HALF_UP).doubleValue();


                branches.stream().parallel().forEach(branch -> {
                    branch.setBranchHoldings(newBranchHoldings);
                    branchRepo.save(branch);
                });

                AccountTransfer transfer = new AccountTransfer(request.getAmount(),customer,customerAccount.getAccountNumber(),
                        "ONLINE",request.getTransactionType(),LocalDateTime.ofInstant(instant, ZoneId.systemDefault()),"Outside of Bank",
                        true,currentDate,CardType.ACCOUNT_TRANSFER_PLACEHOLDER);

                accountTransferRepo.save(transfer);
                checkingAccountRepo.save(customerAccount);
                bankRepo.save(bank.get(0));
                return new TransactionNotification(
                        customer.getEmail(),
                        request.getTransactionType(),
                        request.getDateOfTransaction(),
                        request.getAmount(),
                        "User outside of bank",
                        customer.getFirstName() + " " + customer.getLastName(),
                        false
                );
            }else{
                String message = "Debit transfer to " + transferee.get().getFirstName()  + " " + transferee.get().getLastName();

                CheckingAccount transfereeAccount = (CheckingAccount) transferee.get().getAccounts().stream().parallel().filter(account1 -> account1.getBankAccountType().equals(BankAccountType.CHECKING)).findFirst()
                        .orElse(null);

                if(transfereeAccount == null){
                    return null;
                }

                transfereeAccount.deposit(request.getAmount());
                customerAccount.withdraw(request.getAmount());

                AccountTransfer transfer = new AccountTransfer(request.getAmount(),customer,customerAccount.getAccountNumber(),
                        "ONLINE",request.getTransactionType(),LocalDateTime.ofInstant(instant, ZoneId.systemDefault()),transferee.get().getFirstName()  + " " + transferee.get().getLastName(),
                        true,currentDate,CardType.ACCOUNT_TRANSFER_PLACEHOLDER);

                accountTransferRepo.save(transfer);
                checkingAccountRepo.saveAll(List.of(transfereeAccount,customerAccount));

                return new TransactionNotification(
                        customer.getEmail(),
                        request.getTransactionType(),
                        request.getDateOfTransaction(),
                        request.getAmount(),
                        transferee.get().getEmail(),
                        transferee.get().getFirstName() + " " + transferee.get().getLastName(),
                        customer.getFirstName() + " " + customer.getLastName(),
                        true
                );
            }
        }

        return null;
    }
}
