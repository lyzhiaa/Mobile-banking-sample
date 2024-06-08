package co.istad.mobilebanking.domain;

import co.istad.mobilebanking.domain.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transations")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Account owner;

    @ManyToOne
    private Account receiver;

    private String paymentReceiver;// StudentReceiver

    @Column(nullable = false)
    private BigDecimal Amount;

    @Column(nullable = false, length = 20)
    private String transactionType; //Payment, Transfer
    @Column(nullable = false)
    private LocalDateTime transactionAt;
    @Column(nullable = false)
    private Boolean status;
    @Column(nullable = false)
    private Boolean isDeleted;

    private String remark;

}
