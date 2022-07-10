package com.legacybanking.legacyBankingAPI.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    Bank bank;

    @BeforeEach
    public void setUp(){
        bank = new Bank();
    }

    @Test
    void getId() {
        Long id = 1L;
        bank.setId(id);
        assertEquals(id,bank.getId());
    }

    @Test
    void getName() {
        String name = "Legacy Bank";
        bank.setName(name);
        assertEquals(name,bank.getName());
    }

    @Test
    void getCountry() {
        String country = "The United States";
        bank.setCountry(country);
        assertEquals(country,bank.getCountry());
    }

    @Test
    void getState() {
        String state = "New York";
        bank.setState(state);
        assertEquals(state,bank.getState());
    }

    @Test
    void getZipcode() {
        String zipcode = "75034";
        bank.setZipcode(zipcode);
        assertEquals(zipcode,bank.getZipcode());
    }

    @Test
    void getTotalHoldings() {
        Double holdings = 10500000D;
        bank.setTotalHoldings(holdings);
        assertEquals(holdings,bank.getTotalHoldings());
    }

    @Test
    void getBranches() {
        List<Branch> b = new ArrayList<>();
        Branch branch = new Branch(
             1L,
             "Legacy Bank Southwest",
             "United States",
             "Delaware",
             "77777",
                bank,
                1000000D,
                75.34D,
                -45.23D
        );
        b.add(branch);
        bank.setBranches(b);
        assertEquals(b,bank.getBranches());
    }
}