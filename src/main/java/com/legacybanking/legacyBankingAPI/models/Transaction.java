package com.legacybanking.legacyBankingAPI.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "transaction"
)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(
            name = "amount",
            columnDefinition ="Decimal(10,2)" ,
            nullable = false
    )
    private Double amount;
    @ManyToOne(cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    private Customer customer;
    @Column(
            name = "account_number"
    )
    private String accountNumber;
    @Column(
            name = "location",
            nullable = false
    )
    private String location;
    @Column(
            name = "type",
            nullable = false
    )
    private String type;
    @Column(
            name = "date_of_transaction",
            nullable = false
    )
    private LocalDate dateOfTransaction;

    public Transaction(Double amount, Customer customer, String accountNumber, String location, String type, LocalDate dateOfTransaction) {
        this.amount = amount;
        this.customer = customer;
        this.accountNumber = accountNumber;
        this.location = location;
        this.type = type;
        this.dateOfTransaction = dateOfTransaction;
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
