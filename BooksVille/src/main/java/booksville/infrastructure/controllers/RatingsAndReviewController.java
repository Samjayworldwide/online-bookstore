package booksville.infrastructure.controllers;

import booksville.payload.request.RatingsAndReviewRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.RatingResponsePage;
import booksville.payload.response.ViewRatingsResponse;
import booksville.services.RatingAndReviewService;
import booksville.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class RatingsAndReviewController {
    private final RatingAndReviewService ratingAndReviewService;

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<RatingResponsePage>> getRatingsForBook(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @PathVariable Long bookId) {

        return ratingAndReviewService.getAllRatingsForBook(pageNo, pageSize, sortBy, sortDir, bookId);
    }

    @PostMapping("/rate/{id}")
    public ResponseEntity<ApiResponse<String>> ratingABook (@PathVariable("id") Long id, @RequestBody RatingsAndReviewRequest ratingsAndReviewRequest) {
         return ratingAndReviewService.addRatingAndReview(id, ratingsAndReviewRequest);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ApiResponse<List<ViewRatingsResponse>>> viewAllRatingsAndReviewForABook(@PathVariable("id") Long bookId){
        return ratingAndReviewService.viewAllRatingsAndReviewForABook(bookId);
    }
}
