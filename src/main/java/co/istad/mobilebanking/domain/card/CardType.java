package co.istad.mobilebanking.domain.card;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "card_types")
public class CardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Boolean isDeleted;

    @OneToMany(mappedBy = "cardType")
    private List<Card> card;
}
