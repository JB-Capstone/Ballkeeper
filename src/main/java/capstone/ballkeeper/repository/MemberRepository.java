package capstone.ballkeeper.repository;

import capstone.ballkeeper.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByStudentId(String studentId);
    boolean existsByStudentId(String studentId);
}