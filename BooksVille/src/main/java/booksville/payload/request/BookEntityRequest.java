package booksville.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookEntityRequest {

    @Size(min = 1, max = 100, message = "Author's name is too long or short")
    @NotBlank(message = "Name cannot be blank")
    private  String author;

    @NotBlank(message = "Title title is required.")
    @Size(min = 1, max = 100)
    private  String bookTitle;

    @NotNull(message = "Genre is required")
    private String genre;

    @NotNull(message = "cover is required")
    private MultipartFile bookCover;

    @NotNull(message = "bookFile is required")
    private MultipartFile bookFile;

    @NotBlank(message = "Book description is required.")
    @Size(min = 3, max = 2500)
    private String description;

    @NotNull(message = "Book price must be declared.")
    private BigDecimal price;
}
