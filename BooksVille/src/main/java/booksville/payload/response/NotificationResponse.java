package booksville.payload.response;

import booksville.entities.enums.Genre;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private Long id;
    private LocalDateTime dateCreated;
    private Genre genre;
    private String message;
}
