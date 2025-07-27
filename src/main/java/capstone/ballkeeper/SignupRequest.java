package capstone.ballkeeper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String studentId;
    private String name;
    private String password;
    private String phone;
}
