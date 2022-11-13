package com.legacybanking.legacyBankingAPI.models.bankEntity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "bank"
)
public class Bank  {
    @Id
    @SequenceGenerator(
            name= "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    private String country;
    private String state;
    private String zipcode;
    @Column(
            name = "total_holdings",
            columnDefinition = "Decimal default '0.00'"
    )
    private Double totalHoldings;
    @Column(
            name = "branches"
    )
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "bank")
    private List<Branch> branches;


    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", totalHoldings=" + totalHoldings +
                '}';
    }
}
