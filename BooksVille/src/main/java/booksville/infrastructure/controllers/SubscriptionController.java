package booksville.infrastructure.controllers;

import booksville.payload.response.ApiResponse;
import booksville.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/{subscription}")
    public ResponseEntity<ApiResponse<String>> paystack(@PathVariable String subscription) {
        subscriptionService.subscribe(subscription);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                "success"
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<String>> getSubscription() {
        return subscriptionService.getSubscription();
    }
}
