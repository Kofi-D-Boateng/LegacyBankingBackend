package com.legacybanking.legacyBankingAPI.models.securityAndTokens;

import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class VerificationToken {
    @Id
    @SequenceGenerator(
            name= "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;
    @Column(
            nullable = false
    )
    private String token;
    @Column(
            nullable = false
    )
    private LocalDateTime createdAt;
    @Column(
            nullable = false
    )
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public VerificationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, Customer customer) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.customer = customer;
    }
}





