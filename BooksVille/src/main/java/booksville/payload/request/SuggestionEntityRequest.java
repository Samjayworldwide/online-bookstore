package booksville.payload.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionEntityRequest {
    private boolean love;
    private boolean sex;
    private boolean romance;
    private boolean faith;
    private boolean business;
    private boolean politics;
    private boolean travels;
}
