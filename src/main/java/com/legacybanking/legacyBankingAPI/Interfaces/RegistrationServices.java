package com.legacybanking.legacyBankingAPI.Interfaces;

import com.legacybanking.legacyBankingAPI.models.user.Registration;
import org.springframework.web.bind.annotation.RequestBody;

public interface RegistrationServices {
    void register(@RequestBody Registration registration);
    String createCustomerAccountAndCard(@RequestBody Registration model);
}
