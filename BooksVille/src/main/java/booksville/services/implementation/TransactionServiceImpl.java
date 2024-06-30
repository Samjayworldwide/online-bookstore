package booksville.services.implementation;

import booksville.entities.model.BookEntity;
import booksville.entities.model.TransactionEntity;
import booksville.entities.model.UserEntity;
import booksville.infrastructure.exceptions.ApplicationException;
import booksville.payload.request.payment.FlutterWaveRequest;
import booksville.payload.request.payment.PayStackRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.BookEntityResponse;
import booksville.repositories.BookRepository;
import booksville.repositories.CartRepository;
import booksville.repositories.TransactionEntityRepository;
import booksville.services.TransactionService;
import booksville.utils.HelperClass;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionEntityRepository transactionEntityRepository;
    private final HelperClass helperClass;
    private final BookRepository bookEntityRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<ApiResponse<String>> PayStackPayment(PayStackRequest payStackRequest, Long bookId) {
        UserEntity userEntity = helperClass.getUserEntity();

        BookEntity bookEntity = bookEntityRepository
                .findById(bookId)
                .orElseThrow(
                        () -> new ApplicationException("Book not found")
                );

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .amount(bookEntity.getPrice())
                .status(payStackRequest.getStatus())
                .referenceId(payStackRequest.getTrxref())
                .bookEntity(bookEntity)
                .userEntity(userEntity)
                .build();

        TransactionEntity savedTransaction = transactionEntityRepository.save(transactionEntity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                "success",
                                savedTransaction.getStatus()
                        )
                );
    }

    @Override
    public ResponseEntity<ApiResponse<String>> FlutterPayment(FlutterWaveRequest flutterWaveRequest, Long bookId) {
        UserEntity userEntity = helperClass.getUserEntity();

        BookEntity bookEntity = bookEntityRepository
                .findById(bookId)
                .orElseThrow(
                        () -> new ApplicationException("Book not found")
                );

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .amount(bookEntity.getPrice())
                .status(flutterWaveRequest.getStatus())
                .referenceId(flutterWaveRequest.getTx_ref())
                .bookEntity(bookEntity)
                .userEntity(userEntity)
                .build();

        TransactionEntity savedTransaction = transactionEntityRepository.save(transactionEntity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                "success",
                                savedTransaction.getStatus()
                        )
                );
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<String>> PayStackPaymentCart(PayStackRequest payStackRequest) {
        UserEntity userEntity = helperClass.getUserEntity();

        payStackRequest.getBooks().forEach(
                        bookEntityResponse -> transactionEntityRepository.save(
                                TransactionEntity.builder()
                                        .amount(bookEntityResponse.getPrice())
                                        .status(payStackRequest.getStatus() + "_" + bookEntityResponse.getId())
                                        .referenceId(payStackRequest.getTrxref())
                                        .bookEntity(bookEntityRepository.findById(bookEntityResponse.getId()).get())
                                        .userEntity(userEntity)
                                        .build()
                        )
                );

        cartRepository.deleteAllByUserEntity(userEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                "success",
                                "success"
                        )
                );
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<String>> FlutterPaymentCart(FlutterWaveRequest flutterWaveRequest) {
        UserEntity userEntity = helperClass.getUserEntity();

        flutterWaveRequest.getBooks().forEach(
                bookEntityResponse -> transactionEntityRepository.save(
                        TransactionEntity.builder()
                                .amount(bookEntityResponse.getPrice())
                                .status(flutterWaveRequest.getStatus())
                                .referenceId(flutterWaveRequest.getTx_ref() + "_" + bookEntityResponse.getId())
                                .bookEntity(bookEntityRepository.findById(bookEntityResponse.getId()).get())
                                .userEntity(userEntity)
                                .build()
                )
        );

        cartRepository.deleteAllByUserEntity(userEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                "success",
                                "completed"
                        )
                );
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<List<BookEntityResponse>>> getBestSeller() {
        List<Long> purchasedBooksId = transactionEntityRepository.findAll()
                .stream()
                .map(transactionEntity -> transactionEntity.getBookEntity().getId())
                .toList();

        List<BookEntity> purchasedBooks = transactionEntityRepository.findAll()
                .stream()
                .map(TransactionEntity::getBookEntity)
                .toList();

        Set<BookEntity> uniqueBooks = new HashSet<>(purchasedBooks);

        Map<BookEntity, Integer> occurrence = new HashMap<>();

        for (BookEntity book : uniqueBooks) {
            int num = Collections.frequency(purchasedBooksId, book.getId());
            occurrence.put(book, num);
//            System.out.println(num + " " + book);
            System.out.println(occurrence);
        }

        Map<BookEntity, Integer> sortedOccurrence = new LinkedHashMap<>();
        occurrence.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(entry -> sortedOccurrence.put(entry.getKey(), entry.getValue()));

//        Map<Integer, BookEntity> sortedOccurrence = new TreeMap<>(Collections.reverseOrder());
//        sortedOccurrence.putAll(occurrence);

        List<BookEntityResponse> bookEntityResponses = sortedOccurrence.keySet().stream()
                .map(element -> modelMapper.map(element, BookEntityResponse.class))
                .toList();

        System.out.println(purchasedBooks);
        System.out.println(uniqueBooks);
        System.out.println(occurrence);
        System.out.println(sortedOccurrence);
        System.out.println(bookEntityResponses);


        return ResponseEntity.ok(
                    new ApiResponse<>(
                            "success",
                            bookEntityResponses
                    )
                );
    }
}
