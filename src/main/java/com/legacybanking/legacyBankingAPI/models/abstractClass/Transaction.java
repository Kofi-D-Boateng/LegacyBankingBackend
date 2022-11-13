package com.legacybanking.legacyBankingAPI.models.abstractClass;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.enums.TransactionType;
import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Transaction {
    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )@GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    private Long id;

    @Column(
            name = "amount",
            columnDefinition ="Decimal(10,2)" ,
            nullable = false
    )
    private Double amount;

    @ManyToOne(cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "customer",referencedColumnName = "id")
    @ToString.Exclude
    private Customer customer;

    @Column(
            name = "account_number"
    )
    private String accountNumber;
    @Column(
            name = "location_of_transaction",
            nullable = false
    )
    private String location;
    @Column(
            name = "type_of_transaction",
            nullable = false
    )
    private TransactionType transactionType;
    @Column(
            name = "card_type",
            nullable = false
    )
    private CardType cardType;
    @Column(
            name = "date_of_transaction",
            nullable = false
    )
    private LocalDateTime dateOfTransaction;

    @Column(
            name = "date_transaction_posted",
            nullable = false
    )
    private LocalDateTime dateTransactionPosted;

    public Transaction(Double amount, Customer customer, String accountNumber, String location, TransactionType type, LocalDateTime dateOfTransaction
    ,LocalDateTime dateTransactionPosted,CardType cardType) {
        this.amount = amount;
        this.customer = customer;
        this.accountNumber = accountNumber;
        this.location = location;
        this.transactionType = type;
        this.dateOfTransaction = dateOfTransaction;
        this.dateTransactionPosted = dateTransactionPosted;
        this.cardType = cardType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transaction that = (Transaction) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
