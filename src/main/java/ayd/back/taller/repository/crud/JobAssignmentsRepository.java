package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.JobAssignmentsEntity;
import ayd.back.taller.repository.entities.JobAssignmentsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobAssignmentsRepository extends JpaRepository<JobAssignmentsEntity, JobAssignmentsId> {
}
