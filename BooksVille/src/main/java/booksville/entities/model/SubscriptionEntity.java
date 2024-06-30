package booksville.entities.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SubscriptionEntity extends BaseEntity {

    private String subscription;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
