package com.legacybanking.legacyBankingAPI.config.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {
        @Value("${aws.region}")
        private String region;
        @Value("${aws.accessKey}")
        private String accessKey;
        @Value("${aws.secretKey}")
        private String secretKey;
        @Bean
        public AWSLambda awsLambda(){
                BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey);
                AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
                return AWSLambdaClientBuilder.standard()
                        .withCredentials(credentialsProvider)
                        .withRegion(Regions.fromName(region)).build();
        }
}
