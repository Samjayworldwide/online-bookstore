package booksville.services;

import booksville.entities.model.BookEntity;
import booksville.entities.model.Notification;
import booksville.payload.response.NotificationResponse;

import java.util.List;

public interface NotificationCacheService {
    NotificationResponse getNotificationData(Long id);
    NotificationResponse updateCachedData(Notification notification);
}
