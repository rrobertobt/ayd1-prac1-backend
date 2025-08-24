package ayd.back.taller.repository.entities;

import ayd.back.taller.repository.enums.PurchaseStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_orders")
public class PurchaseOrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id",referencedColumnName = "id")
    private UserEntity supplier;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "purchase_status_t")
    private PurchaseStatusEnum status;

    private String description;

    private Double total;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "created_at",insertable = true, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


}
