package com.legacybanking.legacyBankingAPI.models.abstractClass;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Card {
    @Id
    @SequenceGenerator(
            name = "card_sequence",
            sequenceName = "card_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "card_sequence"
    )
    private Long id;
    @Column(
            name = "card_number",
            nullable = false
    )
    private String cardNumber;

    @Column(
            name = "cvc",
            nullable = false
    )
    private String cardVerificationCode;

    @Column(
            name = "expiration_date",
            nullable = false
    )
    private LocalDateTime expirationDate;

    @ManyToOne(cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "customer",referencedColumnName = "id")
    @ToString.Exclude
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "account_number")
    @JsonIgnore
    private Account account;

    @Column(
            name = "card_is_Locked",
            nullable = false
    )
    private Boolean isLocked;

    @Column(
            name = "card_type",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private CardType type;

    public Card(String cardNumber, String cardVerificationCode, LocalDateTime expirationDate, Customer customer, Boolean isLocked, CardType type) {
        this.cardNumber = cardNumber;
        this.cardVerificationCode = cardVerificationCode;
        this.expirationDate = expirationDate;
        this.customer = customer;
        this.isLocked = isLocked;
        this.type = type;
    }

}
