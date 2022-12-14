package com.legacybanking.legacyBankingAPI;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {LegacyBankingApiApplicationTests.class})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
class LegacyBankingApiApplicationTests {
}
