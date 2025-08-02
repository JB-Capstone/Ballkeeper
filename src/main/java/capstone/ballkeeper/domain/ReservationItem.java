package capstone.ballkeeper.domain;

import capstone.ballkeeper.domain.item.Item;
import capstone.ballkeeper.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservation_item")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationItem {

    @Id @GeneratedValue
    @Column(name = "reservation_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
