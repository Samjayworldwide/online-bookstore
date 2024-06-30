package booksville.payload.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequest {
   private String genre;
   private String genre2;
   private String genre3;
   private String genre4;
   private String genre5;
   private String genre6;
   private String genre7;
}
