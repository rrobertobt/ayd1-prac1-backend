package ayd.back.taller.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PurchaseOrderItemId {

    @Column(name = "purchase_order_id")
    private Integer purchaseOrderId;

    @Column(name = "part_id")
    private Integer partId;

}
