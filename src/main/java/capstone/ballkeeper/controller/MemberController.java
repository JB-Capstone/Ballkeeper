package capstone.ballkeeper.controller;

import capstone.ballkeeper.auth.CustomUserDetails;
import capstone.ballkeeper.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/me")
    public Member getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userDetails.getMember();
    }
}
