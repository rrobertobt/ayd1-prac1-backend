package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.JobTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobTaskRepository extends JpaRepository<JobTaskEntity, Integer> {

    @Query(value = "SELECT * FROM job_tasks WHERE status = ?", nativeQuery = true)
    Optional<JobTaskEntity> getJobByStatus(String status);

}
