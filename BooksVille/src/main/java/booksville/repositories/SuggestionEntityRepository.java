package booksville.repositories;

import booksville.entities.model.BookEntity;
import booksville.entities.model.SuggestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SuggestionEntityRepository extends JpaRepository<SuggestionEntity, Long> {

    List<SuggestionEntity> findByUserEntity_Email(String email);

    @Transactional
    @Modifying
    @Query("UPDATE SuggestionEntity s " +
            "SET s.bookEntity = :book " +
            "WHERE s.bookEntity.bookTitle LIKE CONCAT('%', 'love', '%') " +
            "   OR s.bookEntity.bookTitle LIKE CONCAT('%', 'sex', '%') " +
            "   OR s.bookEntity.bookTitle LIKE CONCAT('%', 'romance', '%') " +
            "   OR s.bookEntity.bookTitle LIKE CONCAT('%', 'faith', '%') " +
            "   OR s.bookEntity.bookTitle LIKE CONCAT('%', 'business', '%') " +
            "   OR s.bookEntity.bookTitle LIKE CONCAT('%', 'politics', '%') " +
            "   OR s.bookEntity.bookTitle LIKE CONCAT('%', 'travels', '%') " +
            "AND (s.love = true OR s.sex = true OR s.romance = true OR " +
            "     s.faith = true OR s.business = true OR s.politics = true OR " +
            "     s.travels = true)")
    void updateSuggestionsWithBook(@Param("book") BookEntity book);

}
