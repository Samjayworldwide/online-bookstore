package booksville.payload.request.payment;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaidUser {
    private String name;
    private String email;
    private String phone_number;
}
