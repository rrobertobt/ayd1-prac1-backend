package ayd.back.taller.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Embeddable
public class PurchaseOrdersItemsId {

    @Column(name = "purchase_order_id")
    private Integer purchaseOrderId;

    @Column(name = "part_id")
    private Integer partId;

}
