package booksville.services;

import booksville.entities.model.SuggestionEntity;
import booksville.payload.request.SuggestionEntityRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.SuggestionEntityResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SuggestionService {

    ResponseEntity<ApiResponse<SuggestionEntityResponse>> saveSuggestion(SuggestionEntityRequest suggestionRequest);

    ResponseEntity<ApiResponse<List<SuggestionEntityResponse>>> displaySuggestions(String userEmail, String query);

}
