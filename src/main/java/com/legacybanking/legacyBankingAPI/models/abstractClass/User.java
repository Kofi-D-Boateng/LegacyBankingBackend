package com.legacybanking.legacyBankingAPI.models.abstractClass;

import com.legacybanking.legacyBankingAPI.enums.Department;
import com.legacybanking.legacyBankingAPI.enums.UserRole;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @SequenceGenerator(
            name= "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
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
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Column(
            name = "is_activated",
            nullable = false
    )
    private Boolean isActivated;

    public User(String firstName, String lastName, String password, LocalDate dob, String email, String country, String state, Long zipcode, String socialSecurity, Long phoneNumber, UserRole userRole, Department department, Boolean isActivated) {
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
        this.userRole = userRole;
        this.department = department;
        this.isActivated = isActivated;
    }

    @Override
    public String toString() {
        return "User{" +
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
                ", phoneNumber=" + phoneNumber +
                ", userRole=" + userRole +
                ", department=" + department +
                ", isActivated=" + isActivated +
                '}';
    }
}
