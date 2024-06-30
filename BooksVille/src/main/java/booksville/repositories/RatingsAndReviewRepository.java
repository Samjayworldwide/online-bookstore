package booksville.repositories;

import booksville.entities.model.BookEntity;
import booksville.entities.model.RatingsAndReviewEntity;
import booksville.entities.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingsAndReviewRepository extends JpaRepository<RatingsAndReviewEntity,Long> {
    List<RatingsAndReviewEntity> findByBookEntityId(Long bookId);
    Optional<RatingsAndReviewEntity> findByUserEntityAndBookEntity(UserEntity userEntity, BookEntity bookEntity);
    Page<RatingsAndReviewEntity> findAllByBookEntity(BookEntity bookEntity, Pageable pageable);
}
