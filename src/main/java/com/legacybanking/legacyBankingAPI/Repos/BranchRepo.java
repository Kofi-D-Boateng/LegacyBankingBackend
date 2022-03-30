package com.legacybanking.legacyBankingAPI.Repos;

import com.legacybanking.legacyBankingAPI.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface BranchRepo extends JpaRepository<Branch,Long> {
    Optional<Branch> findByName(String name);
    Optional<Branch> findByCountry(String name);
    Branch findByZipcode(String zipcode);
    Optional<Branch> findByState(String state);
}
