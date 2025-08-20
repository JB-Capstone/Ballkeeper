package capstone.ballkeeper.domain;

import capstone.ballkeeper.domain.item.Item;
import capstone.ballkeeper.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESERVATION_ITEM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationItem {

    @Id @GeneratedValue
    @Column(name = "RESERVATIONITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESERVATION_ID", nullable = false)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;

    /** 정적 생성 메서드: 생성/연결을 한 곳에서 보장 */
    public static ReservationItem of(Reservation reservation, Item item) {
        ReservationItem ri = new ReservationItem();
        ri.setReservation(reservation);
        ri.setItem(item);
        return ri;
    }

    /* 연관관계 세터는 외부에서 막고, 같은 패키지/하위 도메인에서만 쓰도록 */
    void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    void setItem(Item item) {
        this.item = item;
    }
}
