package booksville.entities.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_entity")
public class BookEntity extends BaseEntity{
    private String author;

    private String bookTitle;

    private String genre;

    @Column(length = 2500)
    private String description;

    private String bookCover;

    @Lob
    @Column(length = 2500)
    private byte[] bookData;

    private BigDecimal price;

    private Boolean hidden = false;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.ALL)
    private List<SavedBooksEntity> savedBooksEntities = new ArrayList<>();

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.ALL)
    private List<CartEntity> cartEntities = new ArrayList<>();

    @OneToOne(mappedBy = "bookEntity", cascade = CascadeType.ALL)
    private SuggestionEntity suggestionEntity;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.ALL)
    private List<RatingsAndReviewEntity> ratingsAndReviewEntity = new ArrayList<>();

    private Integer rating = getAverageBookRating();

    @PostLoad
    private void calculateAverageBookRating() {
        rating = getAverageBookRating();
    }

    public int getAverageBookRating() {
        return ratingsAndReviewEntity.stream()
                .mapToInt(RatingsAndReviewEntity::getRating)
                .reduce(0, Integer::sum);
    }
}
