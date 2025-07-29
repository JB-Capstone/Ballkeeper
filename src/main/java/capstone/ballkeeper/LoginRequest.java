package capstone.ballkeeper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String studentId;
    private String password;
}
