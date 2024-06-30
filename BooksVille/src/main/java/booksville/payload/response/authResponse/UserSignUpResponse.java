package booksville.payload.response.authResponse;

import booksville.entities.enums.Gender;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Gender gender;

}
