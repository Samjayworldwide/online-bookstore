package booksville.entities.model;

import booksville.entities.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_table")
public class OrderEntity extends BaseEntity{
    @Column(name = "order_num", length = 22)
    private String orderNum;

    private String deliveryCost;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,CascadeType.MERGE
            ,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "transaction_id")
    private TransactionEntity transactionEntity;

}
