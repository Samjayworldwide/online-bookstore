package booksville.infrastructure.controllers;

import booksville.payload.request.payment.FlutterWaveRequest;
import booksville.payload.request.payment.PayStackRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.BookEntityResponse;
import booksville.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/paystack/{bookId}")
    public ResponseEntity<ApiResponse<String>> paystack(@RequestBody PayStackRequest payStackRequest,
                                           @PathVariable Long bookId) {
        log.info(String.valueOf(payStackRequest), bookId);
        return transactionService.PayStackPayment(payStackRequest, bookId);
    }

    @PostMapping("/flutter/{bookId}")
    public ResponseEntity<ApiResponse<String>> flutter(@RequestBody FlutterWaveRequest flutterWaveRequest,
                                          @PathVariable Long bookId) {
        return transactionService.FlutterPayment(flutterWaveRequest, bookId);
    }

    @PostMapping("/paystack")
    public ResponseEntity<ApiResponse<String>> payStackCart(@RequestBody PayStackRequest payStackRequest) {

        return transactionService.PayStackPaymentCart(payStackRequest);
    }

    @PostMapping("/flutter")
    public ResponseEntity<ApiResponse<String>> flutterCart(@RequestBody FlutterWaveRequest flutterWaveRequest) {

        return transactionService.FlutterPaymentCart(flutterWaveRequest);
    }

    @GetMapping("best-seller")
    public ResponseEntity<ApiResponse<List<BookEntityResponse>>> getBestSeller() {

        return transactionService.getBestSeller();
    }
}