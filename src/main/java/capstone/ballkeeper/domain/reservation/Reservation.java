package capstone.ballkeeper.domain.reservation;

import capstone.ballkeeper.domain.ReservationItem;
import capstone.ballkeeper.domain.UsageStatus;
import capstone.ballkeeper.domain.item.Item;
import capstone.ballkeeper.domain.member.Member;
import capstone.ballkeeper.domain.notification.Notification;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "RESERVATIONS",
        indexes = {
                @Index(name = "ix_reservation_member", columnList = "USER_ID"),
                @Index(name = "ix_reservation_time_range", columnList = "START_TIME, END_TIME")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Long id;

    // 회원과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private Member member;

    // 알림 (예약 1 : N 알림) - 함께 저장/삭제를 원하면 cascade+orphanRemoval
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    // 예약된 물품들 (중간 테이블)
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationItem> reservationItems = new ArrayList<>();

    // 예약 시각
    @Column(name = "START_TIME", nullable = false)
    private LocalDateTime startTime;

    // 종료 시각
    @Column(name = "END_TIME", nullable = false)
    private LocalDateTime endTime;

    // 예약 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 30)
    private ReservationStatus status;

    // 예약자 물품 수령 시각
    @Column(name = "PICKUP_TIME")
    private LocalDateTime pickupTime;

    // 수령 인증 사진
    @Column(name = "PICKUP_PHOTO_URL", length = 200)
    private String pickupPhotoUrl;

    // 관리자 승인 여부
    @Enumerated(EnumType.STRING)
    @Column(name = "USAGE_STATUS", nullable = false, length = 30)
    private UsageStatus usageStatus;

    // 관리자 승인 시각
    @Column(name = "USAGE_AT")
    private LocalDateTime usageAt;

    // 반납 시각
    @Column(name = "RETURN_TIME")
    private LocalDateTime returnTime;

    @Column(name = "RETURN_PHOTO_URL", length = 200)
    private String returnPhotoUrl;

    /* ========= 정적 팩토리 ========= */
    public static Reservation createReservation(
            Member member,
            LocalDateTime startTime,
            LocalDateTime endTime,
            ReservationStatus status,
            UsageStatus usageStatus
    ) {
        if (member == null) throw new IllegalArgumentException("member는 필수입니다.");
        if (startTime == null || endTime == null) throw new IllegalArgumentException("시간은 필수입니다.");
        if (!startTime.isBefore(endTime)) throw new IllegalArgumentException("시작 시각은 종료 시각보다 앞서야 합니다.");

        Reservation r = new Reservation();
        r.setMember(member);
        r.startTime = startTime;
        r.endTime = endTime;
        r.status = status;
        r.usageStatus = usageStatus;
        return r;
    }

    /* ========= 연관관계 편의 메서드 ========= */
    public void setMember(Member member) {
        this.member = member;
        if (member != null && !member.getReservations().contains(this)) {
            member.getReservations().add(this);
        }
    }

    // Reservation.java
    public void addItem(Item item) {
        ReservationItem ri = ReservationItem.of(this, item); // 정적 팩토리
        this.getReservationItems().add(ri);
    }


    public void addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setReservation(this);
    }

    /* ========= 도메인 로직 ========= */
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

