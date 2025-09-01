package ayd.back.taller.service;


import ayd.back.taller.dto.request.JobAssignmentDto;
import ayd.back.taller.dto.request.UpdateJobAssignmentDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.crud.JobAssignmentsRepository;
import ayd.back.taller.repository.crud.UserCrud;
import ayd.back.taller.repository.entities.JobAssignmentsEntity;
import ayd.back.taller.repository.entities.JobAssignmentsId;
import ayd.back.taller.repository.entities.JobEntity;
import ayd.back.taller.repository.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobAssignmentService {

    private final JobAssignmentsRepository jobAssignmentsRepository;


    private final UserService userService;

    private final JobService jobService;

    public void assignJob(JobAssignmentDto jobAssignmentDto){

        UserEntity userEntity = userService.getUserById(jobAssignmentDto.getUserId());
        JobEntity jobEntity = jobService.getJobById(jobAssignmentDto.getJobId());

        JobAssignmentsId jobAssignmentsId = new JobAssignmentsId();
        jobAssignmentsId.setJobId(jobAssignmentDto.getJobId());
        jobAssignmentsId.setUserId(jobAssignmentDto.getUserId());

        JobAssignmentsEntity jobAssignmentsEntity = new JobAssignmentsEntity();
        jobAssignmentsEntity.setId(jobAssignmentsId);
        jobAssignmentsEntity.setUser(userEntity);
        jobAssignmentsEntity.setJob(jobEntity);
        jobAssignmentsEntity.setCreatedAt(LocalDateTime.now());
        jobAssignmentsEntity.setUpdateAt(LocalDateTime.now());

        try{
            jobAssignmentsRepository.save(jobAssignmentsEntity);
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Error saving job assignment");
        }
    }


    public void updateJobAssignment(UpdateJobAssignmentDto updateJobAssignmentDto){
        try{
            jobAssignmentsRepository.updateJobAssignment(updateJobAssignmentDto.getNewUserId(),updateJobAssignmentDto.getUserId(),updateJobAssignmentDto.getJobId());
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error in update employee for job.");
        }
    }


}
