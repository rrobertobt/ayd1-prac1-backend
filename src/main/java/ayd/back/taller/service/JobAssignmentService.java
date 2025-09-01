package ayd.back.taller.service;


import ayd.back.taller.dto.request.JobAssignmentDto;
import ayd.back.taller.repository.crud.JobAssignmentsRepository;
import ayd.back.taller.repository.entities.JobAssignmentsEntity;
import ayd.back.taller.repository.entities.JobAssignmentsId;
import ayd.back.taller.repository.entities.JobEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobAssignmentService {

    private final JobAssignmentsRepository jobAssignmentsRepository;


    public void assignJob(JobAssignmentDto jobAssignmentDto){

        JobAssignmentsId jobAssignmentsId = new JobAssignmentsId();
        jobAssignmentsId.setJobId(jobAssignmentDto.getJobId());
        jobAssignmentsId.setUserId(jobAssignmentDto.getUserId());

        JobAssignmentsEntity jobAssignmentsEntity = new JobAssignmentsEntity();
        jobAssignmentsEntity.setId(jobAssignmentsId);

        // todo continuar con la asignacion...
    }

}
