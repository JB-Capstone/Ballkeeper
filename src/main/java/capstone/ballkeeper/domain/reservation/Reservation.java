package capstone.ballkeeper.domain.reservation;

import capstone.ballkeeper.domain.ReservationItem;
import capstone.ballkeeper.domain.UsageStatus;
import capstone.ballkeeper.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservation")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    // 회원과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    // 예약된 물품들과의 다대다 중간 테이블
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationItem> reservationItems = new ArrayList<>();

    // 예약 시각
    @Column(name = "start_time")
    private LocalDateTime startTime;

    // 종료 시각
    @Column(name = "end_time")
    private LocalDateTime endTime;

    // 예약 상태
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    // 예약자 물품 수령 시각
    @Column(name = "pickup_time")
    private LocalDateTime pickupTime;

    // 수령 인증 사진
    @Column(name = "pickup_photo_url", length = 200)
    private String pickupPhotoUrl;

    // 관리자 승인 여부
    @Column(name = "usage_status")
    @Enumerated(EnumType.STRING)
    private UsageStatus usageStatus;

    // 관리자 승인 시각
    @Column(name = "usage_at")
    private LocalDateTime usageAt;

    // 반납 시각
    @Column(name = "return_time")
    private LocalDateTime returnTime;

    @Column(name = "return_photo_url", length = 200)
    private String returnPhotoUrl;

    // 생성 메서드
    public static Reservation createReservation(Member member, LocalDateTime startTime, LocalDateTime endTime, ReservationStatus status, UsageStatus usageStatus) {
        Reservation reservation = new Reservation();
        reservation.member = member;
        reservation.startTime = startTime;
        reservation.endTime = endTime;
        reservation.status = status;
        reservation.usageStatus = usageStatus;

        return reservation;
    }

    /*
    * 연관관계 메서드
    * */
    public void addReservationItem(ReservationItem item) {
        reservationItems.add(item);
        item.setReservation(this);
    }

    public void changeStatus(ReservationStatus status) {
        this.status = status;
    }

    public void updatePickupInfo(LocalDateTime pickupTime, String photoUrl) {
        this.pickupTime = pickupTime;
        this.pickupPhotoUrl = photoUrl;
    }

    public void updateReturnInfo(LocalDateTime returnTime, String photoUrl) {
        this.returnTime = returnTime;
        this.returnPhotoUrl = photoUrl;
    }

    public void updateUsageStatus(UsageStatus usageStatus, LocalDateTime usageAt) {
        this.usageStatus = usageStatus;
        this.usageAt = usageAt;
    }
}
