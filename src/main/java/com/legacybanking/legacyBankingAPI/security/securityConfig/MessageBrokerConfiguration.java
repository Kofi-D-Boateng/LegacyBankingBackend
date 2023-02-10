package com.legacybanking.legacyBankingAPI.security.securityConfig;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.legacybanking.legacyBankingAPI.utils.LocalDateTimeDeserializer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class MessageBrokerConfiguration {
    public static final String VENDOR_TRANSACTION_QUEUE = "vendor-transaction";
    public static final String VENDOR_TRANSACTION_ROUTING_KEY = "vendor";
    public static final String ATM_TRANSACTION_QUEUE = "atm-transaction";
    public static final String ATM_TRANSACTION_ROUTING_KEY = "atm";
    public static final String ACCOUNT_TRANSFER_QUEUE = "account-transfer";
    public static final String ACCOUNT_TRANSFER_ROUTING_KEY = "account";
    public static final String SECURITY_QUEUE = "update-customer-security";
    public static final String SECURITY_QUEUE_ROUTING_KEY = "security";
    public static final String NOTIFICATIONS_EXCHANGE = "notifications";
    public static final String BANKING_EXCHANGE = "bank";
    public static final String INSERT_NOTIFICATION_ROUTING_KEY = "insert";

    @Bean
    Queue vendorTransactionQueue(){
        return new Queue(VENDOR_TRANSACTION_QUEUE,true);
    }

    @Bean
    Queue atmTransactionQueue(){
        return new Queue(ATM_TRANSACTION_QUEUE,true);
    }

    @Bean
    Queue accountTransferQueue(){
        return new Queue(ACCOUNT_TRANSFER_QUEUE,true);
    }

    @Bean Queue updateCustomerSecurityQueue(){
        return new Queue(SECURITY_QUEUE,true);
    }

    @Bean
    DirectExchange bankDirectExchange(){
        return new DirectExchange(BANKING_EXCHANGE);
    }

    @Bean
    Binding vendorTransactionBinding(Queue vendorTransactionQueue, DirectExchange bankExchange){
        return BindingBuilder.bind(vendorTransactionQueue).to(bankExchange).with(VENDOR_TRANSACTION_ROUTING_KEY);
    }

    @Bean
    Binding atmTransactionBinding(Queue atmTransactionQueue, DirectExchange bankExchange){
        return BindingBuilder.bind(atmTransactionQueue).to(bankExchange).with(ATM_TRANSACTION_ROUTING_KEY);
    }

    @Bean
    Binding accountTransferBinding(Queue accountTransferQueue, DirectExchange bankExchange){
        return BindingBuilder.bind(accountTransferQueue).to(bankExchange).with(ACCOUNT_TRANSFER_ROUTING_KEY);
    }

    @Bean
    Binding securityBinding(Queue updateCustomerSecurityQueue, DirectExchange bankExchange){
        return BindingBuilder.bind(updateCustomerSecurityQueue).to(bankExchange).with(SECURITY_QUEUE_ROUTING_KEY);
    }


    @Bean
    public MessageConverter jsonMessageConverter(){
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        objectMapper.registerModule(module);
        objectMapper.findAndRegisterModules();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return  rabbitTemplate;
    }
}
