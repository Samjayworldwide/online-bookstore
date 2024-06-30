package booksville.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    @NotBlank(message = "Old Password field is required")
    private String oldPassword;

    @NotBlank(message = "New Password field is required")
    private String newPassword;

    @NotBlank(message = "Confirm New Password field is required")
    private String confirmNewPassword;
}
