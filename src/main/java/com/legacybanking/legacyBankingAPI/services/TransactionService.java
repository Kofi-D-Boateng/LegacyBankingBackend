package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.Repos.BankRepo;
import com.legacybanking.legacyBankingAPI.Repos.BranchRepo;
import com.legacybanking.legacyBankingAPI.Repos.CustomerRepo;
import com.legacybanking.legacyBankingAPI.Repos.TransactionRepo;
import com.legacybanking.legacyBankingAPI.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
    private static final String DEPOSIT = "deposit";
    private static final String WITHDRAWAL = "withdrawal";
    private static final String ACHDEBIT = "ACH Debit";
    private static final String ACHCREDIT = "ACH Credit";
    private static final String TRANSFER = "Debit transfer";
    private static final String CREDIT = "Credit";
    private static final String DEBIT = "Debit";
    private final Date date = new Date();
    private final Timestamp timestamp = new Timestamp(date.getTime());
    private final LocalDate currentDate = LocalDate.now();






    @Transactional
    public boolean vendorTransaction(TransactionModel transaction) throws RuntimeException{
       Long transactionCardNumber = transaction.getCardNumber();
       Customer customer = customerRepo.findByCardNumber(transactionCardNumber);
       Double totalFunds = customer.getCapital();
       Double purchaseAmount = transaction.getAmount();
       double amount = totalFunds - purchaseAmount;

       log.info("TRANSACTION: {}", transaction);

       if(customer.getId() == null){
           log.info("Card number not tied to user: {}",transactionCardNumber);
           return false;
       }

       if(amount <= 0){
           return false;
       }

        if(transaction.getCvc() != customer.getCvc()){
            log.info("Faulty transaction attempted at this time:{} at location:{}",timestamp,transaction.getLocation());
            return false;
        }

        if(!customer.getEnabled() || customer.getLocked()){
            log.info("Faulty transaction attempted at this time:{} at location:{}",timestamp,transaction.getLocation());
            return false;
        }

        if(transaction.getExpirationDate().isBefore(currentDate)){
            log.info("Faulty transaction attempted at this time:{} at location:{}",timestamp,transaction.getLocation());
            return false;
        }

//      ADD A CREDIT LINE TO THE DATABASE SO YOU CAN HANDLE CREDIT CARD REFUNDS
        if(transaction.getIsRefund()){
            if(transaction.getType().equals(DEBIT)){
                customer.setCapital(customer.getCapital() + transaction.getAmount());
                transactionRepo.save(new Transaction(
                        transaction.getAmount(),
                        customer,
                        customer.getAccountNumber(),
                        transaction.getLocation(),
                        transaction.getType(),
                        transaction.getDateOfTransaction()
                ));
                customerRepo.save(customer);
                return true;
            }
        }

       customer.setCapital(amount);
       transactionRepo.save(new Transaction(
               transaction.getAmount(),
               customer,
               customer.getAccountNumber(),
               transaction.getLocation(),
               transaction.getType(),
               transaction.getDateOfTransaction()
       ));
       customerRepo.save(customer);
       return true;
    }

    @Transactional
    public boolean atmTransaction(TransactionModel transaction){
        Branch branch = branchRepo.findByZipcode(transaction.getLocation());
        Bank bank = bankRepo.getById(branch.getBank().getId());
        Customer customer = customerRepo.findByCardNumber(transaction.getCardNumber());
        Double branchFunds = branch.getBranchHoldings();


        log.info("BRANCH:{}", branch);
        log.info("BANK:{}", bank);
        log.info("CUSTOMER:{}",customer);
        log.info("TRANSACTION: {}", transaction);

        if(customer.getId() == null){
            log.info("Card number not tied to user: {}, timestamp:{}",
                    transaction.getCardNumber(), timestamp);
            return false;
        }

        if(branch.getName() == null){
            log.info("Faulty transaction attempted at this location:{}, timestamp:{}",
                    transaction.getLocation(), timestamp);
            return false;
        }
        if(transaction.getAmount() <=0 || transaction.getAmount().isNaN()){
            return false;
        }
        if(branchFunds - transaction.getAmount() <= 0){
            log.info("Branch:{} is currently low on funds., timestamp:{}",
                    branch.getName(), timestamp);
            return false;
        }
        if(bank.getTotalHoldings() - branchFunds <= 0){
            log.info("Legacy's current holdings are: {}, timestamp:{}",
                    bank.getTotalHoldings(), timestamp);
            return false;
        }

        if(transaction.getCvc() != customer.getCvc()){
            log.info("Faulty transaction attempted at this time:{} at location:{}",timestamp,transaction.getLocation());
            return false;
        }

        if(!customer.getEnabled() || customer.getLocked()){
            log.info("Faulty transaction attempted at this time:{} at location:{}",timestamp,transaction.getLocation());
            return false;
        }

        if(transaction.getExpirationDate().isBefore(currentDate)){
            log.info("Faulty transaction attempted at this time:{} at location:{}",timestamp,transaction.getLocation());
            return false;
        }

        if(transaction.getType().equals(WITHDRAWAL)){
            if(customer.getCapital() - transaction.getAmount() < 0){
                log.info("CURRENT USER_ID:{} funds '{}' are too low for transaction",customer.getId(),customer.getCapital());
                return false;
            }
            transaction.setLocation(branch.getName());
            branch.setBranchHoldings(branchFunds - transaction.getAmount());
            bank.setTotalHoldings(bank.getTotalHoldings() - (branchFunds - branch.getBranchHoldings()));
            customer.setCapital(customer.getCapital() - transaction.getAmount());

            transactionRepo.save(new Transaction(
                    transaction.getAmount(),
                    customer,
                    customer.getAccountNumber(),
                    transaction.getLocation(),
                    transaction.getType(),
                    transaction.getDateOfTransaction()
            ));
            branchRepo.save(branch);
            bankRepo.save(bank);
            customerRepo.save(customer);

            return true;
        }
        if(transaction.getType().equals(DEPOSIT)){
            transaction.setLocation(branch.getName());
            branch.setBranchHoldings(branchFunds + transaction.getAmount());
            bank.setTotalHoldings(bank.getTotalHoldings() + (branch.getBranchHoldings() - branchFunds));
            customer.setCapital(customer.getCapital() + transaction.getAmount());

            transactionRepo.save(new Transaction(
                    transaction.getAmount(),
                    customer,
                    customer.getAccountNumber(),
                    transaction.getLocation(),
                    transaction.getType(),
                    transaction.getDateOfTransaction()
            ));
            branchRepo.save(branch);
            bankRepo.save(bank);
            customerRepo.save(customer);
            return true;
        }

        log.info("Faulty transaction attempted at this location:{}",transaction.getLocation());
        return false;
    }

    @Transactional
    public TransactionNotification accountTransfer(TransactionModel transaction){
        List<Bank> bank = bankRepo.findAll();
        List<Branch> branches = branchRepo.findAll();
        Customer customer = customerRepo.findByAccountNumber(transaction.getAccountNumber());

        log.info("TRANSACTION: {}", transaction);
        log.info("CUSTOMER: {}",customer);

        if(!customer.getEnabled()){
            log.info("Faulty transaction attempted at this time:{}",timestamp);
            return new TransactionNotification();
        }


        if(transaction.getType().equals(TRANSFER)){


            Optional<Customer> transferee = transaction.getPhoneNumberOfTransferee() != null
                    ? customerRepo.findTransfereeByPhoneNumber(transaction.getPhoneNumberOfTransferee())
                    : customerRepo.findByEmail(transaction.getEmailOfTransferee());
            log.info("TRANSFEE:{}",transferee);

            if(transferee.isEmpty()){
                String message = "Debit transfer";
                transaction.setLocation(message);
                customer.setCapital(customer.getCapital() - transaction.getAmount());
                bank.get(0).setTotalHoldings(bank.get(0).getTotalHoldings()-transaction.getAmount());
                BigDecimal bankHoldings = BigDecimal.valueOf(bank.get(0).getTotalHoldings());
                Double newBranchHoldings = bankHoldings.multiply(BigDecimal.valueOf(100)).divide(new BigDecimal(branches.size()),2,RoundingMode.HALF_UP).doubleValue();


                for (Branch branch: branches) {
                    branch.setBranchHoldings(newBranchHoldings);
                    branchRepo.save(branch);
                }

                transactionRepo.save(new Transaction(
                        transaction.getAmount(),
                        customer,
                        customer.getAccountNumber(),
                        transaction.getLocation(),
                        transaction.getType(),
                        transaction.getDateOfTransaction()
                ));
                customerRepo.save(customer);
                bankRepo.save(bank.get(0));
                return new TransactionNotification(
                        customer.getEmail(),
                        transaction.getType(),
                        LocalDateTime.now(),
                        transaction.getAmount(),
                        transaction.getEmailOfTransferee(),
                        customer.getFirstName() + " " + customer.getLastName(),
                        false
                );
            }else{
                log.info("TRANSFEE:{}",transferee.get());
                transaction.setIsTransferringToOutsideBank(false);
            }

            if(!transaction.getIsTransferringToOutsideBank()){
                String message = "Debit transfer to " + transferee.get().getFirstName()  + " " + transferee.get().getLastName();
                transaction.setLocation(message);
                transferee.get().setCapital(transferee.get().getCapital() + transaction.getAmount());
                customer.setCapital(customer.getCapital() - transaction.getAmount());
                transactionRepo.save(new Transaction(
                        transaction.getAmount(),
                        customer,
                        customer.getAccountNumber(),
                        transaction.getLocation(),
                        transaction.getType(),
                        transaction.getDateOfTransaction()
                ));
                customerRepo.saveAll(List.of(customer,transferee.get()));
                return new TransactionNotification(
                        customer.getEmail(),
                        transaction.getType(),
                        LocalDateTime.now(),
                        transaction.getAmount(),
                        transferee.get().getEmail(),
                        transferee.get().getFirstName() + " " + transferee.get().getLastName(),
                        customer.getFirstName() + " " + customer.getLastName(),
                        true
                );
            }
        }

        if(transaction.getType().equals(ACHDEBIT)){
            String message = "Debit transfer to " + transaction.getLocation();
            customer.setCapital(customer.getCapital() - transaction.getAmount());
            transactionRepo.save(new Transaction(
                    transaction.getAmount(),
                    customer,
                    customer.getAccountNumber(),
                    message,
                    transaction.getType(),
                    transaction.getDateOfTransaction()
            ));
            customerRepo.save(customer);
            return new TransactionNotification(
                    customer.getEmail(),
                    transaction.getType(),
                    LocalDateTime.now(),
                    transaction.getAmount(),
                    transaction.getLocation(),
                    customer.getFirstName() + " " + customer.getLastName(),
                    false
            );
        }

        if(transaction.getType().equals(ACHCREDIT)){
            String message = "Credit transfer from " + transaction.getLocation();
            transaction.setLocation(message);
            customer.setCapital(customer.getCapital() + transaction.getAmount());
            bank.get(0).setTotalHoldings(bank.get(0).getTotalHoldings()+ transaction.getAmount());
            transactionRepo.save(new Transaction(
                    transaction.getAmount(),
                    customer,
                    customer.getAccountNumber(),
                    message,
                    transaction.getType(),
                    transaction.getDateOfTransaction()
            ));
            customerRepo.save(customer);
            bankRepo.save(bank.get(0));

            return new TransactionNotification(
                    customer.getEmail(),
                    transaction.getType(),
                    LocalDateTime.now(),
                    transaction.getAmount(),
                    customer.getFirstName() + " " + customer.getLastName(),
                    transaction.getLocation(),
                    false
            );
        }
        return new TransactionNotification();
    }
}
