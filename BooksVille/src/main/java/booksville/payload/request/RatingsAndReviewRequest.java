package booksville.payload.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingsAndReviewRequest {
    private Integer rating;
    private String review;
}
