package com.legacybanking.legacyBankingAPI.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Column(name = "branch_customers")
    @OneToMany
    @JoinTable(
            name = "branch_customer",
            joinColumns = @JoinColumn(name = "branch_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private List<Customer> branchCustomers;
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
