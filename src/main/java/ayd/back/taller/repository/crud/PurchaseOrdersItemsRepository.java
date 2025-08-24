package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.PurchaseOrdersEntity;
import ayd.back.taller.repository.entities.PurchaseOrdersItemsEntity;
import ayd.back.taller.repository.entities.PurchaseOrdersItemsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrdersItemsRepository extends JpaRepository<PurchaseOrdersItemsEntity, PurchaseOrdersItemsId> {

}
