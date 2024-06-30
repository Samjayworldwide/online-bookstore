package booksville.infrastructure.controllers;

import booksville.payload.response.NotificationResponse;
import booksville.services.NotificationCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationCacheService cacheService;

    @GetMapping
    public NotificationResponse getNotifications() {
        return cacheService.getNotificationData(1L);
    }
}