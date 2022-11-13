package com.legacybanking.legacyBankingAPI.models.customer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Account;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Card;
import com.legacybanking.legacyBankingAPI.enums.CustomerRole;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Transaction;
import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "customer"
)
public class Customer {
    @Id
    @SequenceGenerator(
            name= "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    private Long id;
    @Column(
            name = "first_name",
            nullable = false
    )

    private String firstName;
    @Column(
            name = "last_name",
            nullable = false
    )

    private String lastName;
    @Column(
            name = "password",
            nullable = false
    )

    private String password;
    @Column(
            name = "date_of_birth",
            nullable = false
    )

    private LocalDate dob;
    @Column(
            name = "email",
            nullable = false,
            unique = true
    )

    private String email;
    @Column(
            name = "country",
            nullable = false
    )
    private String country;
    @Column(
            name = "state",
            nullable = false
    )
    private String state;
    @Column(
            name = "zip_code",
            nullable = false
    )
    private Long zipcode;
    @Column(
            name = "social_security",
            nullable = false,
            unique = true
    )
    private String socialSecurity;

    @Column(
            name = "phone_number",
            nullable = false
    )
    private Long phoneNumber;


    @Enumerated(EnumType.STRING)
    private CustomerRole customerRole;

    @Column(
            name = "transactions"
    )
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    @JsonManagedReference
    private List<Transaction> transactions;


    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "customer")
    Set<Account> accounts = new HashSet<>();

    @Column(
            name="cards"
    )
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "customer")
    @JsonManagedReference
    private Set<Card> cards = new HashSet<>();

    @Column(
            name = "is_activated",
            nullable = false
    )
    private Boolean isActivated;

    @Column(
            name = "account_pin",
            nullable = false
    )
    private Long accountPin;

    public Customer(String firstName, String lastName, String password, LocalDate dob, String email, String country, String state,
                    Long zipcode, String socialSecurity, Long phoneNumber, CustomerRole customerRole, Boolean isActivated,
                    Long accountPin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.dob = dob;
        this.email = email;
        this.country = country;
        this.state = state;
        this.zipcode = zipcode;
        this.socialSecurity = socialSecurity;
        this.phoneNumber = phoneNumber;
        this.customerRole = customerRole;
        this.isActivated = isActivated;
        this.accountPin = accountPin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return id != null && Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
