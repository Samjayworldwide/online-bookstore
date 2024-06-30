package booksville.repositories;

import booksville.entities.model.BookEntity;
import booksville.entities.model.CartEntity;
import booksville.entities.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    List<CartEntity> findCartEntitiesByUserEntity(UserEntity userEntity);
    Optional<CartEntity> findByUserEntityAndBookEntity(UserEntity user, BookEntity book);
    void deleteByUserEntityAndBookEntity(UserEntity user, BookEntity book);
    void deleteAllByUserEntity(UserEntity userEntity);
}
