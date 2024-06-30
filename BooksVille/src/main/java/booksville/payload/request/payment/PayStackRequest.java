package booksville.payload.request.payment;

import booksville.payload.response.BookEntityResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayStackRequest {
    private String message;
    private String redirecturl;
    private String reference;
    private String status;
    private String trans;
    private String transaction;
    private String trxref;
    private List<BookEntityResponse> books;
}
