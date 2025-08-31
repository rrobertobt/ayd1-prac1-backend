package ayd.back.taller.service;

import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.crud.JobTaskRepository;
import ayd.back.taller.repository.entities.JobTaskEntity;
import ayd.back.taller.repository.enums.TaskStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class JobTaskService {

    private final JobTaskRepository jobTaskRepository;

    public JobTaskEntity getJobByStatus(TaskStatusEnum taskStatusEnum){
        Optional<JobTaskEntity> optionalJobTaskEntity = jobTaskRepository.getJobByStatus(taskStatusEnum.getStatus());

        if(optionalJobTaskEntity.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Job task not found");
        }

        return optionalJobTaskEntity.get();
    }


}
