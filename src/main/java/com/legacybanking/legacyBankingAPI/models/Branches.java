//package com.legacybanking.legacyBankingAPI.models;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.Id;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class Branches {
//    @Id
//    private Long id;
//    private String name;
//    private String country;
//    private String state;
//    private String zipcode;
//    private List<Customer> customers;
//
//    public Branches(String name, String country, String state, String zipcode) {
//        this.name = name;
//        this.country = country;
//        this.state = state;
//        this.zipcode = zipcode;
//        this.customers = new ArrayList<>();
//    }
//
//    public Branches(String country, String state, String zipcode) {
//        this.country = country;
//        this.state = state;
//        this.zipcode = zipcode;
//        this.customers = new ArrayList<>();
//    }
//
//}
