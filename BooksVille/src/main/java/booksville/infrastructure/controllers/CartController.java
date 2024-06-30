package booksville.infrastructure.controllers;

import booksville.payload.response.ApiResponse;
import booksville.payload.response.BookEntityResponse;
import booksville.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<BookEntityResponse>>> getCart() {

        return cartService.getCart();
    }

    @GetMapping("/already-purchased")
    public ResponseEntity<ApiResponse<List<BookEntityResponse>>> checkAlreadyPurchasedBooksInCart() {
        return cartService.checkAlreadyPurchasedBooksInCart();
    }

    @PostMapping("/addToCart")
    public ResponseEntity<ApiResponse<String>> addToCart(@RequestParam("id") Long id) {

        return cartService.addToCart(id);
    }

    @DeleteMapping("/removeFromCart/{id}")
    public ResponseEntity<ApiResponse<String>> removeFromCart(@PathVariable("id") Long id) {

        return cartService.removeFromCart(id);
    }

    @DeleteMapping("/already-purchased")
    ResponseEntity<ApiResponse<String>> removeAllAlreadyPurchased(@RequestParam List<Long> ids) {

        return cartService.removeAllAlreadyPurchased(ids);
    }
}
