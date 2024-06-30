package booksville.payload.response;

import lombok.*;

/**
 * DTO for {@link booksville.entities.model.UserEntity}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntityResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePicture;
    private String phoneNumber;
    private String gender;
}