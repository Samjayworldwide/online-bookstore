package booksville.services;

import booksville.payload.request.BookEntityRequest;
import booksville.payload.request.FilterRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.BookEntityResponse;
import booksville.payload.response.BookResponsePage;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface BookService {
    ResponseEntity<ApiResponse<BookEntityResponse>> findById(Long id);
    ResponseEntity<ApiResponse<BookResponsePage>> getAllBooks(int pageNo, int pageSize, String sortBy, String sortDir);
    ResponseEntity<ApiResponse<BookEntityResponse>> addBook(BookEntityRequest bookEntityRequest) throws IOException;
    ResponseEntity<ApiResponse<BookEntityResponse>> editBook(BookEntityRequest bookEntityRequest, Long bookEntityId);
    ResponseEntity<ApiResponse<String>> deleteBook(Long bookId);
    ResponseEntity<ApiResponse<Boolean>> toggleHideBook(Long bookId);
    ResponseEntity<ApiResponse<BookResponsePage>> getSavedBooks(int pageNo, int pageSize, String sortBy, String sortDir);
    ResponseEntity<ApiResponse<String>> saveBook(Long id);
    ResponseEntity<ApiResponse<String>> removeSavedBook(Long id);
    byte[] downloadBook (Long bookId);
    ResponseEntity<ApiResponse<BookResponsePage>> getPurchasedBooks(int pageNo, int pageSize, String sortBy, String sortDir);
    ResponseEntity<ApiResponse<BookResponsePage>> searchUsingAuthorOrTitleOrGenre(int pageNo, int pageSize, String sortBy, String sortDir, String search);
    ResponseEntity<ApiResponse<List<BookEntityResponse>>> filterBooksByGenreAndRating(FilterRequest filterRequest);
    ResponseEntity<ApiResponse<BookResponsePage>> filterByRating(int pageNo, int pageSize, String sortBy, String sortDir, int rating);
    ResponseEntity<ApiResponse<List<BookEntityResponse>>> findAllBooks();
    ResponseEntity<ApiResponse<List<BookEntityResponse>>> getPurchasedBooksByUser();
}
