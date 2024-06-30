package booksville.infrastructure.controllers;

import booksville.payload.request.SuggestionEntityRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.SuggestionEntityResponse;
import booksville.services.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/suggestions")
@RequiredArgsConstructor
public class SuggestionController {
    private final SuggestionService suggestionService;

    @GetMapping()
    public ResponseEntity<ApiResponse<SuggestionEntityResponse>> saveSuggestion(@RequestBody SuggestionEntityRequest suggestionEntityRequest){
        return suggestionService.saveSuggestion(suggestionEntityRequest);
    }

    @GetMapping("/display/{userEmail}")
    public ResponseEntity<ApiResponse<List<SuggestionEntityResponse>>> displaySuggestions(
            @PathVariable String userEmail, @RequestParam("query") String query ) {
        return suggestionService.displaySuggestions(userEmail, query);
    }
}
