package com.legacybanking.legacyBankingAPI.models;

import com.legacybanking.legacyBankingAPI.Repos.CustomerRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
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
    private double capital;
    @Column(
            name = "isLocked",
            columnDefinition = "default 'false'",
            nullable = false
    )
    private boolean locked;
    @Column(
            name = "isEnabled",
            columnDefinition = "default 'true'",
            nullable = false
    )
    private boolean enabled;
    @Column(
            name = "account_number",
            columnDefinition = "VARCHAR(50) default null"
    )
    private String accountNumber;
    @Column(
            name = "routing_number",
            columnDefinition = "VARCHAR(50) default null"
    )
    private String routingNumber;
    @Enumerated(EnumType.STRING)
    private CustomerRole customerRole;
//    @Enumerated(EnumType.ORDINAL)
//    @Column(
//            name = "transactions"
//    )
//    private List<Double> transactionsList;


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
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", zipcode=" + zipcode +
                ", socialSecurity='" + socialSecurity + '\'' +
                ", capital=" + capital +
                ", customerRole=" + customerRole +
                '}';
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
