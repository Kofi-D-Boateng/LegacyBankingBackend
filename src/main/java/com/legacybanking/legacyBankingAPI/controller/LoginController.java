package com.legacybanking.legacyBankingAPI.controller;
import com.legacybanking.legacyBankingAPI.models.LoginRequest;
import com.legacybanking.legacyBankingAPI.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/v1/customer/")
@AllArgsConstructor
public class LoginController {

   private LoginService loginService;
   @PostMapping("login")
    public UserDetails login(@RequestBody LoginRequest request){
       return loginService.login(request);
   }
}
