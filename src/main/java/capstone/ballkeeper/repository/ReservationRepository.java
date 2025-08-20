package capstone.ballkeeper.repository;

import capstone.ballkeeper.domain.reservation.Reservation;
import capstone.ballkeeper.domain.reservation.ReservationStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    // 예약 저장 (신규: persist / 수정: merge)
    public void save(Reservation reservation) {
        if (reservation.getId() == null) {
            em.persist(reservation);
        } else {
            em.merge(reservation);
        }
    }

    // 예약 단건 조회
    public Reservation findOne(Long id) {
        return em.find(Reservation.class, id);
    }

    // 전체 예약 조회
    public List<Reservation> findAll() {
        return em.createQuery("select r from Reservation r", Reservation.class)
                .getResultList();
    }

    // 회원별 예약 조회 (memberId로 직접 조회)
    public List<Reservation> findByMemberId(Long memberId) {
        return em.createQuery(
                        "select r from Reservation r where r.member.memberId = :memberId",
                        Reservation.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    // 예약 상태별 조회
    public List<Reservation> findByStatus(ReservationStatus status) {
        return em.createQuery(
                        "select r from Reservation r where r.status = :status",
                        Reservation.class)
                .setParameter("status", status)
                .getResultList();
    }

    // (선택) 회원+상태 복합 조회
    public List<Reservation> findByMemberIdAndStatus(Long memberId, ReservationStatus status) {
        return em.createQuery(
                        "select r from Reservation r " +
                                "where r.member.memberId = :memberId and r.status = :status",
                        Reservation.class)
                .setParameter("memberId", memberId)
                .setParameter("status", status)
                .getResultList();
    }

    // 중복 예약 존재 여부 조회
    public boolean existsOverlapping(Long itemId, LocalDateTime start, LocalDateTime end) {
        String jpql =
                "select count(r) " +
                        "from Reservation r " +
                        "join r.reservationItems ri " +
                        "where ri.item.id = :itemId " +
                        "and r.status in (:activeStatuses) " +
                        "and r.endTime  > :start " +  // 기존 종료가 새 시작 이후
                        "and r.startTime < :end";     // 기존 시작이 새 종료 이전 → 구간 겹침

        Long cnt = em.createQuery(jpql, Long.class)
                .setParameter("itemId", itemId)
                .setParameter("start", start)
                .setParameter("end", end)
                .setParameter("activeStatuses",
                        List.of(ReservationStatus.RESERVED, ReservationStatus.IN_USE))
                .getSingleResult();

        return cnt != null && cnt > 0L;
    }
}
