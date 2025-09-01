package ayd.back.taller.repository.crud;


import ayd.back.taller.repository.entities.JobPartsEntity;
import ayd.back.taller.repository.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPartsRepository extends JpaRepository<JobPartsEntity, Integer> {

    List<JobPartsEntity> findByJob(JobEntity job);

}
