package ayd.back.taller.service;


import ayd.back.taller.dto.request.JobAssignmentDto;
import ayd.back.taller.repository.crud.JobAssignmentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobAssignmentService {

    private final JobAssignmentsRepository jobAssignmentsRepository;


    public void assignJob(JobAssignmentDto jobAssignmentDto){

    }

}
