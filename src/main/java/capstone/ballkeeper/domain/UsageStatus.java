package capstone.ballkeeper.domain;

public enum UsageStatus {
    PENDING,   // 관리자 미인증
    APPROVED,  // 관리자 인증 완료
    REJECTED   // 관리자 인증 거부
}
