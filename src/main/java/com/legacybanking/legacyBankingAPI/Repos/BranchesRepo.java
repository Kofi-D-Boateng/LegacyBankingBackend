//package com.legacybanking.legacyBankingAPI.Repos;
//
//import com.legacybanking.legacyBankingAPI.models.Branches;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Repository
//@Transactional(readOnly = true)
//public interface BranchesRepo extends JpaRepository<Branches,Long> {
//    Optional<Branches> findByName(String name);
//    Optional<Branches> findByCountry(String name);
//    Optional<Branches> findByZipcode(String zipcode);
//    Optional<Branches> findByState(String state);
//}
