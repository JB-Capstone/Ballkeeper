package capstone.ballkeeper.domain.reservation;

public enum ReservationStatus {
    AVAILABLE, // 사용 가능
    RESERVED,  // 예약됨
    IN_USE,    // 사용 중
    COMPLETED, // 반납 완료
}
