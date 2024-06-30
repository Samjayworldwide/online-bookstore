package booksville.entities.model;

import booksville.entities.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users_table")
public class UserEntity extends BaseEntity{
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @Transient
    private String confirmPassword;

    private String phoneNumber;

    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    private boolean isVerified = false;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<SavedBooksEntity> savedBooksEntities = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<CartEntity> cartEntities = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<RatingsAndReviewEntity> ratingsAndReviewEntity = new ArrayList<>();

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private SubscriptionEntity subscriptionEntity;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<SuggestionEntity> suggestionEntity = new ArrayList<>();

}
