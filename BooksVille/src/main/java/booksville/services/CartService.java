package booksville.services;

import booksville.payload.response.ApiResponse;
import booksville.payload.response.BookEntityResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    ResponseEntity<ApiResponse<String>> addToCart(Long bookId);
    ResponseEntity<ApiResponse<String>> removeFromCart(Long bookId);
    ResponseEntity<ApiResponse<List<BookEntityResponse>>> getCart();
    ResponseEntity<ApiResponse<List<BookEntityResponse>>> checkAlreadyPurchasedBooksInCart();
    ResponseEntity<ApiResponse<String>> removeAllAlreadyPurchased(List<Long> ids);
}
