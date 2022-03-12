package com.legacybanking.legacyBankingAPI.models;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Branch {
    @Id
    private Long id;
    private String name;
    private String country;
    private String state;
    private String zipcode;
    private ArrayList<Customer> customers;

    public Branch(String name, String country, String state, String zipcode) {
        this.name = name;
        this.country = country;
        this.state = state;
        this.zipcode = zipcode;
        this.customers = new ArrayList<>();
    }

    public Branch(String country, String state, String zipcode) {
        this.country = country;
        this.state = state;
        this.zipcode = zipcode;
        this.customers = new ArrayList<>();
    }

}
