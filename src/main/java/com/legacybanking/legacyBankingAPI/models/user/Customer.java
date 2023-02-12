package com.legacybanking.legacyBankingAPI.models.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.legacybanking.legacyBankingAPI.enums.Department;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Account;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Card;
import com.legacybanking.legacyBankingAPI.enums.UserRole;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Transaction;
import com.legacybanking.legacyBankingAPI.models.abstractClass.User;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "customer"
)
public class Customer extends User {

    @Column(
            name = "transactions"
    )
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @JsonManagedReference
    private List<Transaction> transactions;


    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "customer")
    Set<Account> accounts = new HashSet<>();

    @Column(
            name="cards"
    )
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "customer")
    @JsonManagedReference
    private Set<Card> cards = new HashSet<>();

    @Column(
            name = "account_pin",
            nullable = false
    )
    private Long accountPin;

    public Customer(String firstName, String lastName, String password, LocalDate dob, String email, String country, String state, Long zipcode, String socialSecurity, Long phoneNumber, UserRole userRole, Department department, Boolean isActivated, Long accountPin) {
        super(firstName, lastName, password, dob, email, country, state, zipcode, socialSecurity, phoneNumber, userRole, department, isActivated);
        this.accountPin = accountPin;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Customer{" + super.toString() +
                "transactions=" + transactions +
                ", accounts=" + accounts +
                ", cards=" + cards +
                ", accountPin=" + accountPin +
                "} ";
    }
}
