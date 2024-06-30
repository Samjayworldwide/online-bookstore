package booksville.infrastructure.controllers;

import booksville.payload.request.authRequest.ForgotPasswordRequest;
import booksville.payload.request.authRequest.ForgotPasswordResetRequest;
import booksville.payload.request.authRequest.LoginRequest;
import booksville.infrastructure.security.JWTGenerator;
import booksville.payload.request.authRequest.UserSignUpRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.authResponse.JwtAuthResponse;
import booksville.payload.response.authResponse.UserSignUpResponse;
import booksville.services.AuthService;
import booksville.utils.HelperClass;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JWTGenerator jwtGenerator;
    private final HelperClass helperClass;

    @PostMapping("/register-user")
    public ResponseEntity<ApiResponse<UserSignUpResponse>> registerUser(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        return authService.registerUser(userSignUpRequest);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<ApiResponse<UserSignUpResponse>> registerAdmin(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        return authService.registerAdmin(userSignUpRequest);
    }

    @PostMapping("/admin-forgot-password")
    public ResponseEntity<ApiResponse<String>> adminForgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return authService.forgotPassword(forgotPasswordRequest.getEmail());
    }

    @PostMapping("/user-forgot-password")
    public ResponseEntity<ApiResponse<String>> userForgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return authService.forgotPassword(forgotPasswordRequest.getEmail());
    }

    @PostMapping(value = "/admin-reset-forgot-password")
    public ResponseEntity<ApiResponse<String>> adminResetForgotPassword(@Valid @RequestBody ForgotPasswordResetRequest forgotPasswordResetRequest) {
        return authService.resetForgotPassword(forgotPasswordResetRequest);
    }

    @PostMapping(value = "/user-reset-forgot-password")
    public ResponseEntity<ApiResponse<String>> userResetForgotPassword(@Valid @RequestBody ForgotPasswordResetRequest forgotPasswordResetRequest) {
        return authService.resetForgotPassword(forgotPasswordResetRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtAuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return authService.login(loginRequest);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<ApiResponse<JwtAuthResponse>> adminLogin(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.adminLogin(loginRequest);
    }

    @GetMapping("/logout")
    private ResponseEntity<ApiResponse<String>> logout(){
        authService.logout();
        return ResponseEntity.ok(new ApiResponse<>("Logout Successfully"));
    }

    @GetMapping(value = "/verify-email", produces = MediaType.TEXT_HTML_VALUE)
    public String verifyToken(@RequestParam("token") String token) {
        ResponseEntity<ApiResponse<String>> response = authService.verifyToken(token);

        String email = jwtGenerator.getEmailFromJWT(token);
        String action = "Books Ville | Email Verification";
        String serviceProvider = "Books Ville Customer Portal Service";
        String description = Objects.requireNonNull(response.getBody()).getResponseMessage();

        String htmlResponse = helperClass.emailVerification(email, action, "Go to Login Page", serviceProvider, description);

        String state = Objects.requireNonNull(response.getBody()).getResponseData();

        if (state.equals("valid")) {
            return htmlResponse;
        }
        return "invalid";
    }
}
