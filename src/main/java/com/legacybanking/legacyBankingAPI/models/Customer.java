package com.legacybanking.legacyBankingAPI.models;

import com.legacybanking.legacyBankingAPI.Repos.CustomerRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Customer implements UserDetails {
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
    private String firstName;
    private String lastName;
    private String password;
    private LocalDate dob;
    private String email;
    private String country;
    private String state;
    private Long zipcode;
    private String socialSecurity;
    @Column(
            name = "personal_funds",
            columnDefinition = "Decimal(10,2) default `0.00"
    )
    private double capital;
    @Enumerated(EnumType.STRING)
    private CustomerRole customerRole;
    @Enumerated(EnumType.ORDINAL)
    @Column(
            name = "transactions",
            nullable = true
    )
    private List<Double> transactionsList;
    public Customer(String firstName, String lastName, String password, LocalDate dob, String email, String country, String state, Long zipcode, String socialSecurity, CustomerRole customerRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.dob = dob;
        this.email = email;
        this.country = country;
        this.state = state;
        this.zipcode = zipcode;
        this.socialSecurity = socialSecurity;
        this.customerRole = customerRole;
//        this.transactionsList = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(customerRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

//    @Override
//    public String toString() {
//        return "Customer{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", dob=" + dob +
//                ", email='" + email + '\'' +
//                ", country='" + country + '\'' +
//                ", state='" + state + '\'' +
//                ", zipcode=" + zipcode +
//                ", socialSecurity='" + socialSecurity + '\'' +
//                '}';
//    }

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
