package com.legacybanking.legacyBankingAPI.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "bank"

)
public class Bank  {
    @Id
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
            name = "branches",
            nullable = true
    )
   @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "bank")
    private Set<Branch> branches;


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
