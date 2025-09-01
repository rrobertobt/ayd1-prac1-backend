package ayd.back.taller.service;

import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.crud.JobAssignmentsRepository;
import ayd.back.taller.repository.crud.JobRepository;
import ayd.back.taller.repository.entities.JobAssignmentsEntity;
import ayd.back.taller.repository.entities.JobEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class JobService {


    private final JobRepository jobRepository;

    private final VehicleService vehicleService;
    private final JobAssignmentsRepository jobAssignmentsRepo;

    public void validateEmployeeIsRelatedToJob(Integer employeeId, JobEntity job) {
        List<JobAssignmentsEntity> jobAssignments = jobAssignmentsRepo.findByJob(job);
        boolean userAssigned = false;
        for (JobAssignmentsEntity j: jobAssignments) {
            userAssigned = userAssigned || (employeeId == j.getUser().getId());
        }
        if (!userAssigned) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions");
        }
    }

}
