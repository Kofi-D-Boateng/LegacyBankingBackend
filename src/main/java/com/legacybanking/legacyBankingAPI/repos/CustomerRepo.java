package com.legacybanking.legacyBankingAPI.repos;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional(readOnly = false)
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);
    @Query("select c from Customer c where c.phoneNumber = ?1")
    Optional<Customer> findTransfereeByPhoneNumber(Long PN);
    @Query("select c from Customer c where c.id = ?1")
    Optional<Customer> findByCustomerId(Long customerId);
}
