package co.istad.mobilebanking.domain.account;

import co.istad.mobilebanking.domain.Transaction;
import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.domain.card.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String alias;
    private String actName;
    private String actNo;
    private BigDecimal balance;
    private BigDecimal transferLimit;
    private Boolean isHidden;
    private Boolean isDeleted;

    @ManyToOne
    private AccountType accountType;
    @OneToOne
    private Card card;

    @ManyToOne
    @JoinTable(
            name = "user_accounts",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private User user;
    @OneToMany(mappedBy = "owner")
    private List<Transaction> transactions;
}
