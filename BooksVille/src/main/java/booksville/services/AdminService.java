package booksville.services;

import booksville.payload.request.BookEntityRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.BookEntityResponse;
import booksville.payload.response.BookResponsePage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    ResponseEntity<ApiResponse<BookEntityResponse>> addBook(BookEntityRequest bookEntityRequest);
    ResponseEntity<ApiResponse<BookResponsePage>> getAllBooks(int pageNo, int pageSize, String sortBy, String sortDir);
    ResponseEntity<ApiResponse<List<BookEntityResponse>>> searchBooks(String query);

    ResponseEntity<ApiResponse<String>> hideBook(Long bookId);
}
