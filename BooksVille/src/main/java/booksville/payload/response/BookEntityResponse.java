package booksville.payload.response;

import booksville.entities.model.RatingsAndReviewEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookEntityResponse {
    private Long id;
    private LocalDateTime dateCreated;
    private String author;
    private String bookTitle;
    private String genre;
    private String description;
    private BigDecimal price;
    private String bookCover;
    private Boolean hidden;
    private Integer rating;
    private Boolean isPurchased;
}