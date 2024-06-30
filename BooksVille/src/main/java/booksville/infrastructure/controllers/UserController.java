package booksville.infrastructure.controllers;

import booksville.payload.request.BookFilterRequest;
import booksville.payload.request.ChangePasswordRequest;
import booksville.payload.request.UserEntityRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.BookEntityResponse;
import booksville.payload.response.BookResponsePage;
import booksville.payload.response.UserEntityResponse;
import booksville.services.UserService;
import booksville.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<ApiResponse<UserEntityResponse>> getUser() {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success",
                        mapper.map(userService.getUserEntity(), UserEntityResponse.class)
                )
        );
    }

    @GetMapping("/search/title-or-author-or-price-or-genre")
    public ResponseEntity<ApiResponse<List<BookEntityResponse>>> bookSearchWithKeyword(@RequestParam("query") String query) {
        return userService.searchBooks(query);
    }

    @GetMapping("/filter-books")
    public ResponseEntity<ApiResponse<BookResponsePage>> bookSearchWithFilter(
            @Valid @RequestBody BookFilterRequest bookFilterRequest,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

        return userService.filterBooks(pageNo, pageSize, sortBy, sortDir, bookFilterRequest);
    }

    @PutMapping("/account-info")
    public ResponseEntity<ApiResponse<String>> userInfoUpdate(@RequestBody UserEntityRequest userEntityRequest) {

        return userService.userInfoUpdate(userEntityRequest);
    }

    @PatchMapping("/profile-pic")
    public ResponseEntity<ApiResponse<String>> profilePicUpdate(@RequestParam MultipartFile profilePic) throws IOException {

        return userService.profilePicUpdate(profilePic);
    }

    @PatchMapping("change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(changePasswordRequest);
    }
}
