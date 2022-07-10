package com.legacybanking.legacyBankingAPI.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    @Id
    private Long id;
    private String name;
    private String country;
    private String state;
    private String zipcode;
    @ManyToOne
    @JoinColumn(name = "bank_id")
    @JsonBackReference
    private Bank bank;
    @Column(
            name="branch_holdings",
            nullable = false
    )
    private Double branchHoldings;
    private Double longitude;
    private Double latitude;

}
