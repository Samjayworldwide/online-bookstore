package booksville.repositories;

import booksville.entities.model.SubscriptionEntity;
import booksville.entities.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionEntityRepository extends JpaRepository<SubscriptionEntity, Long> {
    Optional<SubscriptionEntity> findByUserEntity(UserEntity userEntity);
}