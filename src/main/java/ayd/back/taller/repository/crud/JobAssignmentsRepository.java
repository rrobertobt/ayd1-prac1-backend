package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.JobAssignmentsEntity;
import ayd.back.taller.repository.entities.JobAssignmentsId;
import ayd.back.taller.repository.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JobAssignmentsRepository extends JpaRepository<JobAssignmentsEntity, JobAssignmentsId> {

    List<JobAssignmentsEntity> findByJob(JobEntity job);

    @Transactional
    @Modifying
    @Query(value = "update job_assignments set user_id  = ? where user_id = ? and job_id = ?;", nativeQuery = true)
    void updateJobAssignment(Integer newUserId, Integer actualUserId, Integer jobId);

    @Query("SELECT j FROM JobEntity j JOIN j.assignments a WHERE a.user.id = :userId")
    List<JobEntity> findJobsByUserId(Integer userId);

}
