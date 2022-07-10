package com.legacybanking.legacyBankingAPI.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BranchTest {

    Branch branch;

    @BeforeEach
    public void setUp(){
        branch = new Branch();
    }

    @Test
    void getId() {
        Long id = 1L;
        branch.setId(id);
        assertEquals(id,branch.getId());
    }

    @Test
    void getName() {
        String name = "Legacy Bank Southwest";
        branch.setName(name);
        assertEquals(name,branch.getName());
    }

    @Test
    void getCountry() {
        String country = "The United States";
        branch.setCountry(country);
        assertEquals(country,branch.getCountry());
    }

    @Test
    void getState() {
        String state = "Delaware";
        branch.setState(state);
        assertEquals(state,branch.getState());
    }

    @Test
    void getZipcode() {
        String zipcode = "77777";
        branch.setZipcode(zipcode);
        assertEquals(zipcode,branch.getZipcode());
    }

    @Test
    void getBank() {
        List<Branch> b = new ArrayList<>();
        Bank bank = new Bank(
                1L,
                "Legacy Bank",
                "The United States",
                "New York",
                "75023",
                1800000000D,
                b
        );
        branch.setBank(bank);
        assertEquals(bank, branch.getBank());
    }

    @Test
    void getBranchHoldings() {
        Double holdings = 100000000D;
        branch.setBranchHoldings(holdings);
        assertEquals(holdings, branch.getBranchHoldings());
    }

    @Test
    void getLongitude() {
        Double longitude = 75.34D;
        branch.setLongitude(longitude);
        assertEquals(longitude, branch.getLongitude());
    }

    @Test
    void getLatitude() {
        Double latitude = -75.34D;
        branch.setLatitude(latitude);
        assertEquals(latitude, branch.getLatitude());
    }
}