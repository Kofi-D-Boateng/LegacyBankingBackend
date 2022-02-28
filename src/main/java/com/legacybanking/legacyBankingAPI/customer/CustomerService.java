package com.legacybanking.legacyBankingAPI.customer;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService implements UserDetailsService {

       private final static String USER_NOT_FOUND = "invalid email or password";
       private final CustomerRepository customerRepository;
       private final BCryptPasswordEncoder bCryptPasswordEncoder;

       @Override
       public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
           return customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
       }
       public String signUpCustomer(Customer customer){
           boolean usedEmail = customerRepository.findByEmail(customer.getEmail()).isPresent();
           if(usedEmail){
               throw new IllegalStateException("Email is taken");
           }
           String encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
           customer.setPassword(encodedPassword);
           customerRepository.save(customer);
           return "";
       };
}
