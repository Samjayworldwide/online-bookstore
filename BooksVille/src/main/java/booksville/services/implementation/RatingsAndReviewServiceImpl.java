package booksville.services.implementation;

import booksville.entities.model.BookEntity;
import booksville.entities.model.RatingsAndReviewEntity;
import booksville.entities.model.UserEntity;
import booksville.infrastructure.exceptions.ApplicationException;
import booksville.payload.request.RatingsAndReviewRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.RatingResponsePage;
import booksville.payload.response.ViewRatingsResponse;
import booksville.repositories.BookRepository;
import booksville.repositories.RatingsAndReviewRepository;
import booksville.services.RatingAndReviewService;
import booksville.utils.HelperClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingsAndReviewServiceImpl implements RatingAndReviewService {
    private final RatingsAndReviewRepository ratingsAndReviewRepository;
    private final BookRepository bookEntityRepository;
    private final HelperClass helperClass;

    @Override
    public ResponseEntity<ApiResponse<String>> addRatingAndReview(Long bookId, RatingsAndReviewRequest ratingsAndReviewRequest) {
        UserEntity userEntity = helperClass.getUserEntity();

        BookEntity bookEntity = bookEntityRepository.findById(bookId)
                .orElseThrow(() -> new ApplicationException("Book with " + bookId + " not found"));

        Optional<RatingsAndReviewEntity> rateAndReview = ratingsAndReviewRepository.findByUserEntityAndBookEntity(userEntity, bookEntity);

        if (rateAndReview.isPresent()) {
            RatingsAndReviewEntity ratingsAndReviewOld = rateAndReview.get();

            ratingsAndReviewOld.setRating(ratingsAndReviewRequest.getRating());
            ratingsAndReviewOld.setReview(ratingsAndReviewRequest.getReview());

            ratingsAndReviewRepository.save(ratingsAndReviewOld);

            return ResponseEntity.ok(
                    new ApiResponse<>("Your rating has been updated")
            );
        }

        RatingsAndReviewEntity ratingsAndReviewEntity = RatingsAndReviewEntity
                .builder()
                .rating(ratingsAndReviewRequest.getRating())
                .review(ratingsAndReviewRequest.getReview())
                .bookEntity(bookEntity)
                .userEntity(userEntity)
                .build();

        ratingsAndReviewRepository.save(ratingsAndReviewEntity);

        bookEntity.setRating(bookEntity.getAverageBookRating());
        bookEntityRepository.save(bookEntity);

        return ResponseEntity.ok(
                new ApiResponse<>("Your feedback was highly appreciated")
        );
    }

    @Override
    public ResponseEntity<ApiResponse<List<ViewRatingsResponse>>> viewAllRatingsAndReviewForABook(Long bookId) {
        List<RatingsAndReviewEntity> ratingsAndReviews = ratingsAndReviewRepository.findByBookEntityId(bookId);
        List<ViewRatingsResponse> viewRatingsResponseList = ratingsAndReviews.stream().map(ratingAndReview -> ViewRatingsResponse
                .builder()
                .rating(ratingAndReview.getRating())
                .review(ratingAndReview.getReview())
                .firstName(ratingAndReview.getUserEntity().getFirstName())
                .lastName(ratingAndReview.getUserEntity().getLastName())
                .dateCreated(ratingAndReview.getDateCreated())
                .build()).toList();
        return ResponseEntity.ok(new ApiResponse<>("successfully retrieved data", viewRatingsResponseList));
    }

    @Override
    public ResponseEntity<ApiResponse<RatingResponsePage>> getAllRatingsForBook(int pageNo, int pageSize, String sortBy, String sortDir, Long bookId) {
        // Sort condition
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        BookEntity bookEntity = bookEntityRepository
                .findById(bookId)
                .orElseThrow(
                        () -> new ApplicationException("Book not found with id: " + bookId)
                );

        Page<RatingsAndReviewEntity> ratingsAndReviewEntitiesPage = ratingsAndReviewRepository
                .findAllByBookEntity(bookEntity, pageable);

        List<RatingsAndReviewEntity> ratingsAndReviewEntities = ratingsAndReviewEntitiesPage.getContent();

        List<ViewRatingsResponse> ratingsEntityResponses = ratingsAndReviewEntities.stream()
                .map(ratingsAndReviewEntity -> ViewRatingsResponse.builder()
                        .review(ratingsAndReviewEntity.getReview())
                        .rating(ratingsAndReviewEntity.getRating())
                        .firstName(ratingsAndReviewEntity.getUserEntity().getFirstName())
                        .lastName(ratingsAndReviewEntity.getUserEntity().getLastName())
                        .dateCreated(ratingsAndReviewEntity.getDateCreated())
                        .build()
                ).toList();

        RatingResponsePage ratingResponsePage = RatingResponsePage.builder()
                .content(ratingsEntityResponses)
                .pageNo(ratingsAndReviewEntitiesPage.getNumber())
                .pageSize(ratingsAndReviewEntitiesPage.getSize())
                .totalElements(ratingsAndReviewEntitiesPage.getTotalElements())
                .totalPages(ratingsAndReviewEntitiesPage.getTotalPages())
                .last(ratingsAndReviewEntitiesPage.isLast())
                .build();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success",
                        ratingResponsePage
                )
        );
    }
}
