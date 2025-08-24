package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.JobLogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobLogsRepository extends JpaRepository<JobLogsEntity, Integer> {
}
