package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.repos.CustomerRepo;
import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import com.legacybanking.legacyBankingAPI.models.securityAndTokens.SecurityModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {

    SecurityModel securityModel;
    Customer customer;

    @Mock
    CustomerRepo customerRepo;

    @InjectMocks
    SecurityService securityService;

//    @BeforeEach
//    void setUp() {
//        customer = new Customer("Jon","Doe","Password", LocalDate.now(),"email@email.com"
//                ,"The United States","Texas",76012L,"000-00-0000"
//                ,50000.00D,1000000000L,"120034198",
//                "053351821",123453920185762L,134,false,
//                true, CustomerRole.USER);
//        securityModel = new SecurityModel("",true,false,"120034198");
//    }

    @Test
    void setSecurity() {
//        when(customerRepo.findByAccountNumber(securityModel.getAccountNumber())).thenReturn(customer);
//        assertEquals(123453920185762L, (long) customer.getCardNumber());
//
//        boolean check = securityService.setSecurity(securityModel);
//
//        assertTrue(check);
//        assertNull(customer.getCardNumber());
//        assertTrue(customer.getLocked());
    }
}