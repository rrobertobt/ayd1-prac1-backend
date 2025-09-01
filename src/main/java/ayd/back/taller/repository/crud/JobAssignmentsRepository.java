package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.JobAssignmentsEntity;
import ayd.back.taller.repository.entities.JobAssignmentsId;
import ayd.back.taller.repository.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobAssignmentsRepository extends JpaRepository<JobAssignmentsEntity, JobAssignmentsId> {

    List<JobAssignmentsEntity> findByJob(JobEntity job);

}
