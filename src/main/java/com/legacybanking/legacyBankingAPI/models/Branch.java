package com.legacybanking.legacyBankingAPI.models;

import lombok.*;
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
    @ManyToMany
    @JoinTable(
            name = "branch_customer",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    @ToString.Exclude
    private Set<Customer> branchCustomers;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    @ToString.Exclude
    private Bank bank;

}
