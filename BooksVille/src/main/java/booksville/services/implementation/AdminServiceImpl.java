package booksville.services.implementation;

import booksville.entities.model.BookEntity;
import booksville.infrastructure.exceptions.ApplicationException;
import booksville.payload.request.BookEntityRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.BookEntityResponse;
import booksville.payload.response.BookResponsePage;
import booksville.repositories.BookRepository;
import booksville.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    @Override
    public ResponseEntity<ApiResponse<BookEntityResponse>> addBook(BookEntityRequest bookEntityRequest) {
        Optional<BookEntity> bookEntityOptional = bookRepository
                .findByBookTitleAndAuthor(bookEntityRequest.getBookTitle(), bookEntityRequest.getAuthor());

        if (bookEntityOptional.isPresent()) {
            throw new ApplicationException("Book Already Exist");
        }

        BookEntity newBook = bookRepository.save(modelMapper.map(bookEntityRequest, BookEntity.class));

        BookEntityResponse bookResponse = modelMapper.map(newBook, BookEntityResponse.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                "Book created successfully",
                                bookResponse
                        )
                );
    }

    @Override
    public ResponseEntity<ApiResponse<BookResponsePage>> getAllBooks(int pageNo, int pageSize, String sortBy, String sortDir) {
        // Sort condition
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<BookEntity> bookEntitiesPage = bookRepository.findAll(pageable);

        List<BookEntity> bookEntities = bookEntitiesPage.getContent();

        List<BookEntityResponse> bookEntityResponses = bookEntities.stream()
                .map(bookEntityResponse -> modelMapper.map(bookEntityResponse, BookEntityResponse.class))
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        BookResponsePage.builder()
                                .content(bookEntityResponses)
                                .pageNo(bookEntitiesPage.getNumber())
                                .pageSize(bookEntitiesPage.getSize())
                                .totalElements(bookEntitiesPage.getTotalElements())
                                .totalPages(bookEntitiesPage.getTotalPages())
                                .last(bookEntitiesPage.isLast())
                                .build()
                )
        );
    }

    @Override
    public ResponseEntity<ApiResponse<List<BookEntityResponse>>> searchBooks(String query) {
        List<BookEntity> bookSearch = bookRepository.searchBook(query);

        List<BookEntityResponse>searchResponses = bookSearch.stream()
                .map(bookEntity -> {
                    BookEntityResponse bookEntityResponse = modelMapper.map(bookEntity, BookEntityResponse.class);
                    bookEntityResponse.setBookTitle(bookEntity.getBookTitle());
                    bookEntityResponse.setAuthor(bookEntity.getAuthor());
                    bookEntityResponse.setGenre(bookEntity.getGenre());
                    bookEntityResponse.setPrice(bookEntity.getPrice());

                    return bookEntityResponse;
                })
                .toList();
        return ResponseEntity.ok(new ApiResponse<>("search complete",searchResponses));
    }

    @Override
    public ResponseEntity<ApiResponse<String>> hideBook(Long bookId) {
        Optional<BookEntity> optionalBookEntity = bookRepository.findBookEntitiesById(bookId);

        if(optionalBookEntity.isEmpty()){
            throw new ApplicationException("Book with id " +bookId+ " does not exist");
        }

        BookEntity existingBook = optionalBookEntity.get();
        existingBook.setHidden(true);
        bookRepository.save(existingBook);
        return ResponseEntity.ok(new ApiResponse<>("Book with id " + bookId + " is now hidden"));

    }
}