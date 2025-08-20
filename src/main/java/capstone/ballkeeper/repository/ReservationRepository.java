package capstone.ballkeeper.repository;

import capstone.ballkeeper.domain.member.Member;
import capstone.ballkeeper.domain.reservation.Reservation;
import capstone.ballkeeper.domain.reservation.ReservationStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    // 예약 저장
    public void save(Reservation reservation) {
        if (reservation.getId() == null) {
            em.persist(reservation); // 예약이 없을 경우, 영속화
        } else {
            em.merge(reservation);   // 예약이 있을 경우, merge
        }
    }

    // 예약 단건 조회
    public Reservation findOne(Long id) {
        return em.find(Reservation.class, id);
    }

    // 전체 예약 조회
    public List<Reservation> findAll() {
        return em.createQuery("select r from Reservation r", Reservation.class).getResultList();
    }

    // 회원별 예약 조회
    public List<Reservation> findByMember(Member member) {
        return em.createQuery("select r from Reservation r where r.member = :member", Reservation.class)
                .setParameter("member", member)
                .getResultList();
    }


    // 예약 상태별 조회
    public List<Reservation> findByStatus(ReservationStatus status) {
        return em.createQuery("select r from Reservation r where r.status = :status", Reservation.class)
                .setParameter("status", status)
                .getResultList();
    }
}
