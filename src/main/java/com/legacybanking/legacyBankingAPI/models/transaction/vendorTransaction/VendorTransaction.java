package com.legacybanking.legacyBankingAPI.models.transaction.vendorTransaction;


import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.enums.TransactionType;
import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Transaction;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "vendor_transaction"
)
public class VendorTransaction extends Transaction {
    @Column(
            name = "merchant_name",
            nullable = false
    )
    private String merchantName;
    @Column(
            name = "merchant_desc",
            nullable = false
    )
    private String merchantDescription;

    public VendorTransaction(Double amount, Customer customer, String accountNumber, String location, TransactionType type, LocalDateTime dateOfTransaction, String merchantName, String merchantDescription,
                             LocalDateTime dateTransactionPosted, CardType cardType) {
        super(amount, customer, accountNumber, location, type, dateOfTransaction,dateTransactionPosted,cardType);
        this.merchantName = merchantName;
        this.merchantDescription = merchantDescription;
    }
}
