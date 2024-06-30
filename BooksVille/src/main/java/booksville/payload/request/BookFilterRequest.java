package booksville.payload.request;

import booksville.entities.enums.Genre;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookFilterRequest {
    private Genre genre;
    private Integer rating;
}
