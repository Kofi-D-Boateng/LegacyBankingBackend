package com.legacybanking.legacyBankingAPI.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.legacybanking.legacyBankingAPI.Repos.CustomerRole;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


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
            nullable = false
    )
    private String email;
    @Column(
            name = "country",
            nullable = false,
            unique = true
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
            name = "monetary_funds",
            columnDefinition = "Decimal(10,2) default '0.00'",
            nullable = false
    )
    private Double capital;
    @Column(
            name = "isLocked",
            columnDefinition = "default 'false'",
            nullable = false
    )
    private Boolean locked;
    @Column(
            name = "isEnabled",
            columnDefinition = "default 'true'",
            nullable = false
    )
    private Boolean enabled;
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
    private CustomerRole customerRole;
    @Enumerated(EnumType.STRING)
    @Column(
            name = "transactions"
    )
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    @JsonManagedReference
    private List<Transaction> transactions;

    public Customer(String firstName, String lastName, String password, LocalDate dob, String email,
                    String country, String state, Long zipcode, String socialSecurity, double capital,
                  CustomerRole customerRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.dob = dob;
        this.email = email;
        this.country = country;
        this.state = state;
        this.zipcode = zipcode;
        this.socialSecurity = socialSecurity;
        this.capital = capital;
        this.customerRole = customerRole;
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
