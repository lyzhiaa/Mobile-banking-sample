package co.istad.mobilebanking.domain.card;

import co.istad.mobilebanking.domain.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    private Integer typeId;
    private LocalDate expiredAt;
    private LocalDate issuedAt;
    private Boolean isDeleted;
    @Column(length = 3, nullable = false)
    private Integer cvv;
    @Column(length = 100, nullable = false)
    private String holder; //name of owner card
    //set length for varchar| nullable = must have data | unique true = unique (data must different)
    @Column(length = 12, nullable = false, unique = true)
    private String number;

    @OneToOne(mappedBy = "card")
    private Account account;

    @ManyToOne
    private CardType cardType;
}
