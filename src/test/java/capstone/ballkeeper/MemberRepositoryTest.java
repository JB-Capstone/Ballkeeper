package capstone.ballkeeper;

import capstone.ballkeeper.domain.member.Member;
import capstone.ballkeeper.domain.member.Role;
import capstone.ballkeeper.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("사용자 저장 및 조회 테스트")
    void testSaveAndFindUser() {
        // Given
        Member member = Member.builder()
                .studentId("20251234")
                .name("홍길동")
                .password("password123")
                .phone("01012345678")
                .role(Role.MEMBER)
                .build();

        // When
        memberRepository.save(member);
        Member result = memberRepository.findByStudentId("20251234").orElse(null);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("홍길동");
    }
}
