package capstone.ballkeeper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자 저장 및 조회 테스트")
    void testSaveAndFindUser() {
        // Given
        User user = User.builder()
                .studentId("20251234")
                .name("홍길동")
                .password("password123")
                .phone("01012345678")
                .role(User.Role.MEMBER)
                .build();

        // When
        userRepository.save(user);
        User result = userRepository.findByStudentId("20251234").orElse(null);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("홍길동");
    }
}
