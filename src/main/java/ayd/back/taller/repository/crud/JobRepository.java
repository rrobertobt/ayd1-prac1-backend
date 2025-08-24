package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobEntity,Integer> {

}
