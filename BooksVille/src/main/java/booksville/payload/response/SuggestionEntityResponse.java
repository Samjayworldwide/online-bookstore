package booksville.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuggestionEntityResponse {
    private boolean love;
    private boolean sex;
    private boolean romance;
    private boolean faith;
    private boolean business;
    private boolean politics;
    private boolean travels;
}
