package com.legacybanking.legacyBankingAPI.security.securityConfig;

import com.legacybanking.legacyBankingAPI.security.jwt.JWTTokenGeneratorFilter;
//import com.legacybanking.legacyBankingAPI.security.jwt.JwtVerifierFilter;
import com.legacybanking.legacyBankingAPI.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

@Configuration

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {


//    WE ARE USING A DIFFERENT TYPE OF AUTHENTICATION FOR FRONT END USERS


//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final UserDetailsService userDetailsService;
//    private final CustomerService customerService;
//
//    @Autowired
//    public AppSecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, CustomerService customerService){
//        this.userDetailsService = userDetailsService;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.customerService = customerService;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customerService).passwordEncoder(bCryptPasswordEncoder);
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JWTTokenGeneratorFilter jwtTokenGeneratorFilter = new JWTTokenGeneratorFilter(authenticationManager());
        jwtTokenGeneratorFilter.setFilterProcessesUrl("/api/v1/authentication/login");
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:8081"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }) .and()
                .csrf().disable()
//                .addFilterBefore(new JwtVerifierFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilter(jwtTokenGeneratorFilter)
                .authorizeRequests()
                .antMatchers("/api/v1/authentication/**").permitAll()
                .antMatchers("/api/v1/customer/**").hasRole("USER").anyRequest().authenticated();
    }

}
