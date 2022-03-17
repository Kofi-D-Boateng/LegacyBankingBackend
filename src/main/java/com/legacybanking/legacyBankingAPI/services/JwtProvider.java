//package com.legacybanking.legacyBankingAPI.services;
//
//import com.legacybanking.legacyBankingAPI.models.Customer;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.Date;
//
//@Service
//public class JwtProvider {
//
//    public String generateToken(Authentication authentication){
//        Customer principal = (Customer) authentication.getPrincipal();
//        return    Jwts.builder().setSubject(principal.getEmail())
//                .setIssuedAt(new Date())
//                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
//                .signWith(Keys.hmacShaKeyFor("securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecure".getBytes()))
//                .compact();
//    }
//}
