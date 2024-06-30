package booksville.services;

import booksville.payload.request.payment.FlutterWaveRequest;
import booksville.payload.request.payment.PayStackRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.BookEntityResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {
    ResponseEntity<ApiResponse<String>> PayStackPayment(PayStackRequest payStackRequest, Long bookId);
    ResponseEntity<ApiResponse<String>> FlutterPayment(FlutterWaveRequest flutterWaveRequest, Long bookId);
    ResponseEntity<ApiResponse<String>> PayStackPaymentCart(PayStackRequest payStackRequest);
    ResponseEntity<ApiResponse<String>> FlutterPaymentCart(FlutterWaveRequest flutterWaveRequest);
    ResponseEntity<ApiResponse<List<BookEntityResponse>>> getBestSeller();
}
