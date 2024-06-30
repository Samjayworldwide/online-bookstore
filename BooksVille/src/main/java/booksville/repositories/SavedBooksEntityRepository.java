package booksville.repositories;

import booksville.entities.model.BookEntity;
import booksville.entities.model.SavedBooksEntity;
import booksville.entities.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavedBooksEntityRepository extends JpaRepository<SavedBooksEntity, Long> {
    Page<SavedBooksEntity> findSavedBooksEntitiesByUserEntity(UserEntity userEntity, Pageable pageable);
    Optional<SavedBooksEntity> findByUserEntityAndBookEntity(UserEntity userEntity, BookEntity bookEntity);
}