package capstone.ballkeeper.domain;

import capstone.ballkeeper.domain.item.Item;
import capstone.ballkeeper.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RESERVATION_ITEM")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationItem {

    @Id @GeneratedValue
    @Column(name = "RESERVATIONITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESERVATION_ID")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;
}
