package com.legacybanking.legacyBankingAPI.controller;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.google.gson.Gson;
import com.legacybanking.legacyBankingAPI.Interfaces.RegistrationServices;
import com.legacybanking.legacyBankingAPI.config.aws.AWSConfig;
import com.legacybanking.legacyBankingAPI.enums.BankAccountType;
import com.legacybanking.legacyBankingAPI.models.EmailAttributes;
import com.legacybanking.legacyBankingAPI.models.LambdaPayload;
import com.legacybanking.legacyBankingAPI.models.user.Registration;
import com.legacybanking.legacyBankingAPI.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v2/admin/")
@AllArgsConstructor
@Slf4j
public class RegistrationController implements RegistrationServices {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private final AWSConfig awsConfig;




    public static final String FUNCTION = "verificationEmail";

    @PostMapping("register-new-customer")
    public void register(@RequestBody Registration model){
        log.info(String.valueOf(model));
        EmailAttributes emailAttributes = customerService.registerCustomer(model);
        if(emailAttributes != null){
            InvokeRequest request = new InvokeRequest()
                    .withInvocationType(InvocationType.Event)
                    .withPayload(new Gson().toJson(new LambdaPayload(FUNCTION,emailAttributes)))
                    .withFunctionName("notifications");
            awsConfig.awsLambda().invoke(request);
        }else{
            System.out.println("[ERROR]: Could not create email attributes to send to notifications lambda.");
        }
    }

    @PostMapping("registration")
    public String createCustomerAccountAndCard(@RequestBody Registration model){
        log.info("Model: {}",model);
        if(model.getBankAccountType().equals(BankAccountType.CREDIT)) return customerService.createCreditAccount(model);
        else if(model.getBankAccountType().equals(BankAccountType.CHECKING)) return customerService.createCheckingAccount(model);
        else if(model.getBankAccountType().equals(BankAccountType.SAVINGS)) return customerService.createSavingsAccount(model);
        else if(model.getBankAccountType().equals(BankAccountType.CRYPTO)) return "";
        return null;
    }
}
