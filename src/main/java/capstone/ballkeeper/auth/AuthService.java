package capstone.ballkeeper.auth;

import capstone.ballkeeper.repository.MemberRepository;
import capstone.ballkeeper.domain.member.Member;
import capstone.ballkeeper.domain.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByStudentId(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 학번입니다."));

        // 비밀번호 비교
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(member.getStudentId(), member.getRole().name());
        return new LoginResponse(token);
    }

    public void signup(SignupRequest request) {
        if (memberRepository.existsByStudentId(request.getStudentId())) {
            throw new RuntimeException("이미 존재하는 학번입니다.");
        }

        Member member = Member.builder()
                .studentId(request.getStudentId())
                .name(request.getName())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
                .role(Role.MEMBER)
                .createdAt(LocalDateTime.now())
                .build();

        memberRepository.save(member);
    }
}
