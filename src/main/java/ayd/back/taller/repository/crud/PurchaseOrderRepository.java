package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.PurchaseOrderEntity;
import ayd.back.taller.repository.entities.UserEntity;
import ayd.back.taller.repository.enums.PurchaseStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, Integer> {

    List<PurchaseOrderEntity> findByStatus(PurchaseStatusEnum status);

    List<PurchaseOrderEntity> findBySupplier(UserEntity supplier);

    List<PurchaseOrderEntity> findBySupplierAndStatus(UserEntity supplier, PurchaseStatusEnum status);

}
