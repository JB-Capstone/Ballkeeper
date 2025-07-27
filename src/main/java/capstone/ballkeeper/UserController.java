package capstone.ballkeeper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userDetails.getUser();
    }
}
