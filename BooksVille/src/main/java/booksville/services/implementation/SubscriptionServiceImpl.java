package booksville.services.implementation;

import booksville.entities.model.SubscriptionEntity;
import booksville.entities.model.UserEntity;
import booksville.infrastructure.exceptions.ApplicationException;
import booksville.payload.response.ApiResponse;
import booksville.repositories.SubscriptionEntityRepository;
import booksville.services.SubscriptionService;
import booksville.utils.HelperClass;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final HelperClass helperClass;
    private final SubscriptionEntityRepository subscriptionEntityRepository;

    @Override
    public void subscribe(String subscribe) {
        UserEntity userEntity = helperClass.getUserEntity();

        SubscriptionEntity subscriptionEntity = SubscriptionEntity.builder()
                .subscription(subscribe)
                .userEntity(userEntity)
                .build();

        subscriptionEntityRepository.save(subscriptionEntity);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> getSubscription() {
        UserEntity userEntity = helperClass.getUserEntity();

        String subscription = subscriptionEntityRepository
                .findByUserEntity(userEntity)
                .orElseThrow(
                        () -> new ApplicationException("No Subscription Found")
                )
                .getSubscription();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        subscription
                )
        );
    }
}
