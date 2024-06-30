package booksville.services.implementation;

import booksville.entities.model.BookEntity;
import booksville.entities.model.Notification;
import booksville.infrastructure.exceptions.ApplicationException;
import booksville.payload.response.NotificationResponse;
import booksville.repositories.NotificationRepository;
import booksville.services.NotificationCacheService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationCache implements NotificationCacheService {
    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional(readOnly = true)
    @Cacheable("notification")
    public NotificationResponse getNotificationData(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new ApplicationException("Not found")
        );

        return modelMapper.map(notification, NotificationResponse.class);
    }


    @Override
    @Transactional
    @CachePut(value = "notification", key = "#notification.id")
    public NotificationResponse updateCachedData(Notification notification) {

        return modelMapper.map(notification, NotificationResponse.class);
    }
}