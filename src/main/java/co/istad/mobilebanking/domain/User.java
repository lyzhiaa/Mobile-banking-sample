package co.istad.mobilebanking.domain;

import co.istad.mobilebanking.domain.Role;
import co.istad.mobilebanking.domain.account.Account;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;
    @Column(length = 10, nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 6, nullable = false)
    private String gender;
    @Column(nullable = false)
    private String profileImage;
    private String pin;
    @Column(unique = true)
    private String password;
    //================================
    @Column(length = 20, nullable = false, unique = true)
    private String nationalCardId;
    @Column(length = 20, unique = true)
    private String StudentCardId;
    //=================================
    private String street;
    private String village;
    private String sangKatOrCommune;
    private String KhanOrDistrict;
    private String CountryOrProvince;
    //================================
    private String position;
    private BigDecimal monthlyIncomeRange;
    private String employeeType;
    private String companyName;
    private String mainSourceOfIncome;
    //================================
    private Boolean isDeleted;
    private Boolean isBlocked;
    private Boolean isVerified;
    //================================
    @OneToMany(mappedBy = "user")
    private List<Account> account;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

}
