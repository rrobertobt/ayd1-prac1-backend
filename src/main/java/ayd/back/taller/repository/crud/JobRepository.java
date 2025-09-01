package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<JobEntity,Integer> {

    @Query(value = "select * from jobs where status = ?;", nativeQuery = true)
    List<JobEntity> getAllJobsByStatus(String status);

    @Transactional
    @Modifying
    @Query(value = "update jobs set status = 'CANCELADO' where id = ?;", nativeQuery = true)
    void cancelJob(String jobId);

    @Query(value = "SELECT * FROM jobs WHERE id = ?;", nativeQuery = true)
    Optional<JobEntity> findById(Integer id);


    @Transactional
    @Modifying
    @Query(value = "UPDATE jobs SET status = ? WHERE id = ? ;", nativeQuery = true)
    void updateJobStatus(String status, Integer jobId);
}
