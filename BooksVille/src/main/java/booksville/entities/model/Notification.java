package booksville.entities.model;

import booksville.entities.enums.Genre;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Notification extends BaseEntity {
    private String genre;
    private String message;
}
