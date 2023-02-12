package com.legacybanking.legacyBankingAPI.controller;

import com.legacybanking.legacyBankingAPI.models.securityAndTokens.SecurityModel;
import com.legacybanking.legacyBankingAPI.security.securityConfig.MessageBrokerConfiguration;
import com.legacybanking.legacyBankingAPI.services.SecurityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class SecurityListener {
    @Autowired
    private SecurityService securityService;
    @RabbitListener(queues = MessageBrokerConfiguration.SECURITY_QUEUE)
    public boolean configureSecurity(SecurityModel security){
        log.info("CONFIG: {}",security);
        return securityService.setSecurity(security);
    }
}
