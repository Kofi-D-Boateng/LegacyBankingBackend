package com.legacybanking.legacyBankingAPI.registration;

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
