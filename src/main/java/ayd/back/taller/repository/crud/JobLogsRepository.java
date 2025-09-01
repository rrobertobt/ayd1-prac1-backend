package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.JobLogsEntity;
import ayd.back.taller.repository.enums.LogTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobLogsRepository extends JpaRepository<JobLogsEntity, Integer> {

    List<JobLogsEntity> findByJobId(Integer jobId);

    @Query("SELECT j FROM JobLogsEntity j WHERE j.user.id = :userId AND j.logType = :logType")
    List<JobLogsEntity> findByUserAndLogType(Integer userId, LogTypeEnum logType);

}
