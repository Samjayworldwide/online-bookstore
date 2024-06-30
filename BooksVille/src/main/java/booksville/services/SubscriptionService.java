package booksville.services;

import booksville.payload.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface SubscriptionService {
    void subscribe(String subscribe);
    ResponseEntity<ApiResponse<String>> getSubscription();
}
