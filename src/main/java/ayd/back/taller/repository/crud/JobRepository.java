package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<JobEntity,Integer> {

    @Query(value = "SELECT * FROM jobs WHERE id = ?;", nativeQuery = true)
    Optional<JobEntity> findById(Integer id);

}
