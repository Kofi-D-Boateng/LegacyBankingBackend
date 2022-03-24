package com.legacybanking.legacyBankingAPI.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "bank"

)
public class Bank  {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
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
    @Column(
            name = "branches"
    )
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bank")
    private List<Branch> branches;


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
