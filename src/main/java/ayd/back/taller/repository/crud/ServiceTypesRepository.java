package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.ServiceTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypesRepository extends JpaRepository<ServiceTypesEntity, Integer> {
}
