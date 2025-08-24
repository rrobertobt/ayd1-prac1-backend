package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.PartCatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartCatalogRepository extends JpaRepository<PartCatalogEntity, Integer> {

}
