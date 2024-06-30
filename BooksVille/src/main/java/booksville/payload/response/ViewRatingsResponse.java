package booksville.payload.response;

import lombok.*;

import java.time.LocalDateTime;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewRatingsResponse {
    private Integer rating;
    private String review;
    private String firstName;
    private String lastName;
    private LocalDateTime dateCreated;
}
