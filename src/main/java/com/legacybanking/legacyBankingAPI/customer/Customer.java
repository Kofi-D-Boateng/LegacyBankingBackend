package com.legacybanking.legacyBankingAPI.customer;

import java.time.LocalDate;

public class Customer {
    private Long id;
    private String name;
    private LocalDate dob;
    private String email;
    private String country;
    private String state;
    private Long zipcode;
    private String socialSecurity;

    public Customer(Long id, String name, LocalDate dob, String email, String country, String state, Long zipcode, String socialSecurity) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.country = country;
        this.state = state;
        this.zipcode = zipcode;
        this.socialSecurity = socialSecurity;
    }

    public Customer(String name, LocalDate dob, String email, String country, String state, Long zipcode, String socialSecurity) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.country = country;
        this.state = state;
        this.zipcode = zipcode;
        this.socialSecurity = socialSecurity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getZipcode() {
        return zipcode;
    }

    public void setZipcode(Long zipcode) {
        this.zipcode = zipcode;
    }

    public String getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", zipcode=" + zipcode +
                ", socialSecurity='" + socialSecurity + '\'' +
                '}';
    }
}
