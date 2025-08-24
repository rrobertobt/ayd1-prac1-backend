package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.JobTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTaskRepository extends JpaRepository<JobTaskEntity, Integer> {
}
