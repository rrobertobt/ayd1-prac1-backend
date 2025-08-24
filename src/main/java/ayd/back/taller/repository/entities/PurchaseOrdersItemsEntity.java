package ayd.back.taller.repository.entities;


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
@Table(name = "purchase_orders_items")
public class PurchaseOrdersItemsEntity {


    @EmbeddedId
    private PurchaseOrdersItemsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    @MapsId("purchaseOrderId")
    private PurchaseOrdersEntity purchaseOrders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    @MapsId("partId")
    private PartEntity part;

    @Column(name = "unit_price")
    private Double unitPrice;

    private Double amount;

    @Column(name = "created_at", insertable = true, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true)
    @CreationTimestamp
    private LocalDateTime updateAt;

}
