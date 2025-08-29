package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.PartCatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartCatalogRepository extends JpaRepository<PartCatalogEntity, Integer> {

    List<PartCatalogEntity> findBySupplierId(Integer supplierId);

    List<PartCatalogEntity> findBySupplierIdIsNull();

    Optional<PartCatalogEntity> findBySupplierIdAndPartId(Integer supplierId, Integer partId);

}
