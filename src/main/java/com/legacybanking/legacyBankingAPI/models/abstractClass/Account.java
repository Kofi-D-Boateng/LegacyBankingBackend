package com.legacybanking.legacyBankingAPI.models.abstractClass;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.legacybanking.legacyBankingAPI.enums.BankAccountType;
import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Account {
    @Id
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )@GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "customer",referencedColumnName = "id")
    @ToString.Exclude
    private Customer customer;

    @Column(
            name = "account_number",
            columnDefinition = "VARCHAR(255) default null",
            nullable = false
    )
    private String accountNumber;

    @Column(
            name = "routing_number",
            columnDefinition = "VARCHAR(255) default null",
            nullable = false
    )
    private String routingNumber;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "typeOfAccount",
            nullable = false
    )
    private BankAccountType bankAccountType;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "card_number")
    Card card;

    @Column(
            name = "capital_funds",
            columnDefinition = "Decimal(10,2) default '0.00'",
            nullable = false
    )
    private Double capital;

    @Column(
            name = "accountIsEnabled",
            nullable = false
    )
    Boolean isEnabled;

    public Account(Customer customer, String accountNumber, String routingNumber, BankAccountType bankAccountType, Double capital, Boolean isEnabled) {
        this.customer = customer;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.bankAccountType = bankAccountType;
        this.capital = capital;
        this.isEnabled = isEnabled;
    }
}
