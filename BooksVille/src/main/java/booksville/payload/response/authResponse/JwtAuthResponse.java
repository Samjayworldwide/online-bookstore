package booksville.payload.response.authResponse;

import booksville.entities.enums.Roles;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthResponse {
    Long id;
    String firstName;
    String lastName;
    String profilePicture;
    String email;
    String phoneNumber;
    String subscription;
    Roles role;
    String accessToken;
    String refreshToken;
    String tokenType = "Bearer";
}
