package com.legacybanking.legacyBankingAPI.Repos;
import com.legacybanking.legacyBankingAPI.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);
    @Query("select c from Customer c where c.phoneNumber = ?1")
    Optional<Customer> findTransfereeByPhoneNumber(Long PN);
    @Query("select c from Customer c where c.cardNumber = ?1")
    Customer findByCardNumber(Long cardNumber);
    @Query("select c from Customer c where c.accountNumber = ?1")
    Customer findByAccountNumber(String accountNumber);
}
