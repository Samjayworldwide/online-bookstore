package booksville.services;

import booksville.entities.model.UserEntity;
import booksville.payload.request.BookFilterRequest;
import booksville.payload.request.ChangePasswordRequest;
import booksville.payload.request.UserEntityRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.BookEntityResponse;
import booksville.payload.response.BookResponsePage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserEntity getUserEntity();
    ResponseEntity<ApiResponse<List<BookEntityResponse>>> searchBooks(String query);
    ResponseEntity<ApiResponse<BookResponsePage>> filterBooks(
            int pageNo, int pageSize, String sortBy, String sortDir, BookFilterRequest bookFilterRequest
    );
    ResponseEntity<ApiResponse<String>> userInfoUpdate(UserEntityRequest userEntityRequest);
    ResponseEntity<ApiResponse<String>> profilePicUpdate(MultipartFile multipartFile) throws IOException;
    ResponseEntity<ApiResponse<String>> changePassword(ChangePasswordRequest changePasswordRequest);
}
