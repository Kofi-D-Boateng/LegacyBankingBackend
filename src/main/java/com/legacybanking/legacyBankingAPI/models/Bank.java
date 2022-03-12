package com.legacybanking.legacyBankingAPI.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "bank"

)
public class Bank  {
    @Id
    @SequenceGenerator(
            name = "bank_sequence",
            sequenceName = "bank_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bank_sequence"
    )
    private Long id;
    private String name;
    private String country;
    private String state;
    private String zipcode;
    @Column(
            name = "total_holdings",
            columnDefinition = "Decimal(10,2) default '0.00'"
    )
    private double totalHoldings;
    private ArrayList<Branch> branches;

    public Bank(String name, String country, String state, String zipcode, double totalHoldings) {
        this.name = name;
        this.country = country;
        this.state = state;
        this.zipcode = zipcode;
        this.totalHoldings = totalHoldings;
        this.branches = new ArrayList<>();
    }

    public Bank(String name, String country, double totalHoldings) {
        this.name = name;
        this.country = country;
        this.totalHoldings = totalHoldings;
        this.branches = new ArrayList<>();
    }

    public Bank(String name, String country, String state, double totalHoldings) {
        this.name = name;
        this.country = country;
        this.state = state;
        this.totalHoldings = totalHoldings;
        this.branches = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", totalHoldings=" + totalHoldings +
                '}';
    }
}
