package co.istad.mobilebanking.domain.account;

import co.istad.mobilebanking.domain.Transaction;
import co.istad.mobilebanking.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account_types")
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50, nullable = false, unique = true)
    private String alias; //payroll saving-year
    @Column(nullable = false, length = 50)
    private String name; //Payroll SavingYear
    @Column(columnDefinition = "TEXT DEFAULT 'hello'")
    private String description;
    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "accountType")
    private List<Account> account;

}
