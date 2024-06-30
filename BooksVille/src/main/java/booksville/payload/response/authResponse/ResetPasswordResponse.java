package booksville.payload.response.authResponse;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordResponse {

    private Long id;
    private String firstName;
}
