package capstone.ballkeeper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByStudentId(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 학번입니다."));

        // 비밀번호 비교
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getStudentId(), user.getRole().name());
        return new LoginResponse(token);
    }

    public void signup(SignupRequest request) {
        if (userRepository.existsByStudentId(request.getStudentId())) {
            throw new RuntimeException("이미 존재하는 학번입니다.");
        }

        User user = User.builder()
                .studentId(request.getStudentId())
                .name(request.getName())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
                .role(Role.MEMBER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }
}
