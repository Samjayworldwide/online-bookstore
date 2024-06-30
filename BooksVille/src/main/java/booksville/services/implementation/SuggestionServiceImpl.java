package booksville.services.implementation;

import booksville.entities.model.SuggestionEntity;
import booksville.payload.request.SuggestionEntityRequest;
import booksville.payload.response.ApiResponse;
import booksville.payload.response.SuggestionEntityResponse;
import booksville.repositories.SuggestionEntityRepository;
import booksville.repositories.UserEntityRepository;
import booksville.services.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionEntityRepository suggestionRepository;
    private final ModelMapper mapper;
    private final UserEntityRepository userEntityRepository;

    @Override
    public ResponseEntity<ApiResponse<SuggestionEntityResponse>> saveSuggestion(SuggestionEntityRequest suggestionRequest) {
        SuggestionEntity suggestionEntity = SuggestionEntity.builder()
                .love(suggestionRequest.isLove())
                .sex(suggestionRequest.isSex())
                .romance(suggestionRequest.isRomance())
                .faith(suggestionRequest.isFaith())
                .business(suggestionRequest.isBusiness())
                .politics(suggestionRequest.isPolitics())
                .travels(suggestionRequest.isTravels())
                .build();

        SuggestionEntity savedSuggestion = suggestionRepository.save(suggestionEntity);
        SuggestionEntityResponse mappedSavedSuggestion = mapper.map(savedSuggestion, SuggestionEntityResponse.class);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success", mappedSavedSuggestion
                )
        );
    }

    @Override
    public ResponseEntity<ApiResponse<List<SuggestionEntityResponse>>> displaySuggestions(String userEmail, String query) {
        userEntityRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<SuggestionEntity> userSuggestions = suggestionRepository.findByUserEntity_Email(userEmail);

        List<SuggestionEntityResponse> mappedSuggestions = userSuggestions.stream()
                .map(suggestion -> mapper.map(suggestion, SuggestionEntityResponse.class))
                .collect(Collectors.toList());


        return ResponseEntity.ok(
                new ApiResponse<>(
                        "success", mappedSuggestions
                )
        );
    }


}
