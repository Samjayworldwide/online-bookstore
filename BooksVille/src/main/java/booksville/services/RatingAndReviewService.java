package booksville.services;

import booksville.payload.request.RatingsAndReviewRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.RatingResponsePage;
import booksville.payload.response.ViewRatingsResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface RatingAndReviewService {

    ResponseEntity<ApiResponse<String>> addRatingAndReview(Long bookId, RatingsAndReviewRequest ratingsAndReviewRequest);

    ResponseEntity<ApiResponse<List<ViewRatingsResponse>>> viewAllRatingsAndReviewForABook(Long bookId);

    ResponseEntity<ApiResponse<RatingResponsePage>> getAllRatingsForBook(int pageNo, int pageSize, String sortBy, String sortDir, Long bookId);
}
