package com.legacybanking.legacyBankingAPI.models;

import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    Customer customer;

    @BeforeEach
    public void setUp(){
       customer = new Customer();
    }

    @Test
    void getId() {
        Long id = 4L;
        customer.setId(id);
        assertEquals(id, customer.getId());
    }

    @Test
    void getFirstName() {
        String firstName = "Jon";
        customer.setFirstName(firstName);
        assertEquals(firstName,customer.getFirstName());
    }

    @Test
    void getLastName() {
        String lastName = "Doe";
        customer.setLastName(lastName);
        assertEquals(lastName,customer.getLastName());
    }

    @Test
    void getPassword() {
        String password = "password1";
        customer.setPassword(password);
        assertEquals(password,customer.getPassword());
    }

    @Test
    void getDob() {
        LocalDate date = LocalDate.now();
        customer.setDob(date);
        assertEquals(date,customer.getDob());
    }

    @Test
    void getEmail() {
        String password = "password1";
        customer.setPassword(password);
        assertEquals(password,customer.getPassword());
    }

    @Test
    void getCountry() {
        String password = "password1";
        customer.setPassword(password);
        assertEquals(password,customer.getPassword());
    }

    @Test
    void getState() {
        String password = "password1";
        customer.setPassword(password);
        assertEquals(password,customer.getPassword());
    }

    @Test
    void getZipcode() {
        String password = "password1";
        customer.setPassword(password);
        assertEquals(password,customer.getPassword());
    }

    @Test
    void getSocialSecurity() {
        String password = "password1";
        customer.setPassword(password);
        assertEquals(password,customer.getPassword());
    }

    @Test
    void getCapital() {
        String password = "password1";
        customer.setPassword(password);
        assertEquals(password,customer.getPassword());
    }

    @Test
    void getPhoneNumber() {
        String password = "password1";
        customer.setPassword(password);
        assertEquals(password,customer.getPassword());
    }

//    @Test
//    void getAccountNumber() {
//        String accountNumber = "password1";
//        customer.setAccountNumber(accountNumber);
//        assertEquals(accountNumber,customer.getAccountNumber());
//    }
//
//    @Test
//    void getRoutingNumber() {
//        String routingNumber = "5000123403";
//        customer.setRoutingNumber(routingNumber);
//        assertEquals(routingNumber,customer.getRoutingNumber());
//    }
//
//    @Test
//    void getCardNumber() {
//        Long cardNumber = 4040182713326754L;
//        customer.setCardNumber(cardNumber);
//        assertEquals(cardNumber,customer.getCardNumber());
//    }
//
//    @Test
//    void getCvc() {
//        Integer cvc = 321;
//        customer.setCvc(cvc);
//        assertEquals(cvc,customer.getCvc());
//    }
//
//    @Test
//    void getLocked() {
//        Boolean isLocked = true;
//        customer.setLocked(isLocked);
//        assertEquals(isLocked,customer.getLocked());
//    }
//
//    @Test
//    void getEnabled() {
//        Boolean isEnabled = true;
//        customer.setEnabled(isEnabled);
//        assertEquals(isEnabled,customer.getEnabled());
//    }
//
//    @Test
//    void getCustomerRole() {
//        CustomerRole role = CustomerRole.USER;
//        customer.setCustomerRole(role);
//        assertEquals(role,customer.getCustomerRole());
//    }
//
//    @Test
//    void getTransactions() {
//        List<Transaction> t = new ArrayList<>();
//        Transaction transaction = new Transaction(
//                1L,
//                15.45,
//                customer,
//                customer.getAccountNumber(),
//                "Some Location",
//                "Account Withdrawal",
//                LocalDate.now()
//        );
//        t.add(transaction);
//        customer.setTransactions(t);
//        assertEquals(t,customer.getTransactions());
//    }
}