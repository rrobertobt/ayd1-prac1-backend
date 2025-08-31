package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.ServiceTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ServiceTypesRepository extends JpaRepository<ServiceTypesEntity, Integer> {


    @Transactional
    @Modifying
    @Query(value = "UPDATE service_types SET price = ? WHERE id = ?", nativeQuery = true)
    void updatePriceForService(Double price, Integer id);

}
