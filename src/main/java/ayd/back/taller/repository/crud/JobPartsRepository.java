package ayd.back.taller.repository.crud;


import ayd.back.taller.repository.entities.JobPartsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPartsRepository extends JpaRepository<JobPartsEntity, Integer> {
}
