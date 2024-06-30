package booksville.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @NotNull(message = "Amount cannot be null!")
    private BigDecimal amount;

    @NotNull(message = "Transaction Status cannot be null!")
    private String status;

    @NotBlank(message = "Reference Id cannot be blank!")
    private String referenceId;

    @NotNull(message = "Book Id cannot be null!")
    private Long bookEntityId;
}
