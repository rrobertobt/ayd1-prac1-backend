package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.PurchaseOrderItemEntity;
import ayd.back.taller.repository.entities.PurchaseOrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItemEntity, PurchaseOrderItemId> {

    @Query("SELECT i FROM PurchaseOrderItemEntity i WHERE i.purchaseOrder.id = :purchaseOrderId")
    List<PurchaseOrderItemEntity> findByPurchaseOrderId(Integer purchaseOrderId);

}
