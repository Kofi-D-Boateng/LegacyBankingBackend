package com.legacybanking.legacyBankingAPI.controller;
import com.legacybanking.legacyBankingAPI.models.RegistrationRequest;
import com.legacybanking.legacyBankingAPI.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/v1/customer/")
@AllArgsConstructor
public class RegistrationController {


    private RegistrationService registrationService;
    @PostMapping("registration")
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }
}
