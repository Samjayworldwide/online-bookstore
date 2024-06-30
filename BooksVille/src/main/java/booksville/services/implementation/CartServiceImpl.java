package booksville.services.implementation;

import booksville.entities.model.BookEntity;
import booksville.entities.model.CartEntity;
import booksville.entities.model.TransactionEntity;
import booksville.entities.model.UserEntity;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.BookEntityResponse;
import booksville.repositories.BookRepository;
import booksville.repositories.CartRepository;
import booksville.repositories.TransactionEntityRepository;
import booksville.services.CartService;
import booksville.utils.HelperClass;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final TransactionEntityRepository transactionEntityRepository;
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final HelperClass helperClass;
    private final ModelMapper modelMapper;
    private static final String SUCCESS = "success";

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<String>> addToCart(Long bookId) {
        UserEntity user = helperClass.getUserEntity();

        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(()-> new IllegalArgumentException("Book not found"));

        Optional<CartEntity> existingCartItem = cartRepository.findByUserEntityAndBookEntity(user, book);

        if (existingCartItem.isPresent()) {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                          "alreadyAdded"
                    )
            );

        } else {

            //adds fresh book if it's not in cart
            CartEntity newCartItem = new CartEntity();

            newCartItem.setUserEntity(user);
            newCartItem.setBookEntity(book);

            cartRepository.save(newCartItem);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            SUCCESS
                    )
            );
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<String>> removeFromCart(Long bookId) {
        UserEntity user = helperClass.getUserEntity();

        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(()-> new IllegalArgumentException("Book not found"));

        Optional<CartEntity> checkCart = cartRepository.findByUserEntityAndBookEntity(user, book);

        if (checkCart.isEmpty()){
            throw new IllegalArgumentException("No book in cart");
        }

        cartRepository.deleteByUserEntityAndBookEntity(user, book);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        SUCCESS
                )
        );
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<List<BookEntityResponse>>> getCart() {
        UserEntity userEntity = helperClass.getUserEntity();

        List<BookEntityResponse> bookEntityResponses = cartRepository
                .findCartEntitiesByUserEntity(userEntity)
                .stream()
                .map(
                        cartEntity -> modelMapper
                                .map(cartEntity.getBookEntity(), BookEntityResponse.class)
                ).toList();

        return ResponseEntity.ok()
                .body(
                        new ApiResponse<>(
                                SUCCESS,
                                bookEntityResponses
                        )
                );
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<List<BookEntityResponse>>> checkAlreadyPurchasedBooksInCart() {
        UserEntity userEntity = helperClass.getUserEntity();

        List<BookEntity> purchasedBooks = transactionEntityRepository
                .findAllByUserEntity(userEntity)
                .stream()
                .map(TransactionEntity::getBookEntity)
                .toList();

        List<BookEntity> booksInCart = cartRepository
                .findCartEntitiesByUserEntity(userEntity)
                .stream()
                .map(CartEntity::getBookEntity)
                .toList();

        List<BookEntityResponse> bookEntityResponses = booksInCart.stream()
                .filter(purchasedBooks::contains)
                .toList()
                .stream()
                .map(bookEntity -> modelMapper.map(bookEntity, BookEntityResponse.class))
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        SUCCESS,
                        bookEntityResponses
                )
        );
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<String>> removeAllAlreadyPurchased(List<Long> ids) {
        UserEntity userEntity = helperClass.getUserEntity();

        ids.forEach(
                    id -> cartRepository
                    .deleteByUserEntityAndBookEntity(
                            userEntity,
                            bookRepository.findById(id).get()
                    )
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success"
                )
        );
    }
}
