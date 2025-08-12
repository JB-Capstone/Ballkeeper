package capstone.ballkeeper.domain.reservation;

import capstone.ballkeeper.domain.ReservationItem;
import capstone.ballkeeper.domain.UsageStatus;
import capstone.ballkeeper.domain.member.Member;
import capstone.ballkeeper.domain.notification.Notification;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RESERVATIONS")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Long id;

    // 회원과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Member member;

    // 알림과의 연관관계
    @OneToMany(mappedBy = "reservation")
    private List<Notification> notifications = new ArrayList<>();

    // 예약된 물품들과의 다대다 중간 테이블
    @OneToMany(mappedBy = "reservation")
    private List<ReservationItem> reservationItems = new ArrayList<>();

    // 예약 시각
    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    // 종료 시각
    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    // 예약 상태
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    // 예약자 물품 수령 시각
    @Column(name = "PICKUP_TIME")
    private LocalDateTime pickupTime;

    // 수령 인증 사진
    @Column(name = "PICKUP_PHOTO_URL", length = 200)
    private String pickupPhotoUrl;

    // 관리자 승인 여부
    @Column(name = "USAGE_STATUS")
    @Enumerated(EnumType.STRING)
    private UsageStatus usageStatus;

    // 관리자 승인 시각
    @Column(name = "USAGE_AT")
    private LocalDateTime usageAt;

    // 반납 시각
    @Column(name = "RETURN_TIME")
    private LocalDateTime returnTime;

    @Column(name = "RETURN_PHOTO_URL", length = 200)
    private String returnPhotoUrl;

    // 연관 관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        if (!member.getReservations().contains(this)) {
            member.getReservations().add(this);
        }
    }

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

    public void addNotification(Notification notification) {
        notifications.add(notification);
        notification.setReservation(this);
    }
}
