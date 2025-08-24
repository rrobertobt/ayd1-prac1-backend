package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.PartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<PartEntity, Integer> {
}
