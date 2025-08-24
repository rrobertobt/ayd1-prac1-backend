package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.SpecialtiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtiesRepository extends JpaRepository<SpecialtiesEntity, Integer> {

}
