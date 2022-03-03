//package com.legacybanking.legacyBankingAPI.models;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.Column;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class Bank  {
//    private Long id;
//    private String name;
//    private String country;
//    private String state;
//    private String zipcode;
//    @Column(
//            name = "total_holdings",
//            columnDefinition = "Decimal(10,2) default `0.00"
//    )
//    private double totalHoldings;
//    private List<Branches> branches;
//
//    public Bank(String name, String country, String state, String zipcode, double totalHoldings) {
//        this.name = name;
//        this.country = country;
//        this.state = state;
//        this.zipcode = zipcode;
//        this.totalHoldings = totalHoldings;
//        this.branches = new ArrayList<>();
//    }
//
//    public Bank(String name, String country, double totalHoldings) {
//        this.name = name;
//        this.country = country;
//        this.totalHoldings = totalHoldings;
//        this.branches = new ArrayList<>();
//    }
//
//    public Bank(String name, String country, String state, double totalHoldings) {
//        this.name = name;
//        this.country = country;
//        this.state = state;
//        this.totalHoldings = totalHoldings;
//        this.branches = new ArrayList<>();
//    }
//}
