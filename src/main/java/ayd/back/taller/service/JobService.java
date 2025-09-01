package ayd.back.taller.service;

import ayd.back.taller.dto.request.CreateJobDto;
import ayd.back.taller.dto.request.jobs.UpdateJobStatusDto;
import ayd.back.taller.dto.response.JobDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.crud.JobRepository;
import ayd.back.taller.repository.crud.JobAssignmentsRepository;
import ayd.back.taller.repository.crud.UserCrud;
import ayd.back.taller.repository.entities.JobAssignmentsEntity;
import ayd.back.taller.repository.entities.JobEntity;
import ayd.back.taller.repository.entities.UserEntity;
import ayd.back.taller.repository.entities.VehicleEntity;
import ayd.back.taller.repository.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class JobService {


    private final JobRepository jobRepository;

    private final VehicleService vehicleService;
    private final JobAssignmentsRepository jobAssignmentsRepo;
    private final UserCrud userRepo;
    private final SessionService sessionService;


    public List<JobDto> getJobsForUser(String token) {
        var session = sessionService.validateSessionToken(token);
        UserEntity user = userRepo.getUserByEmail(session.getEmail())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        List<JobEntity> jobs = new ArrayList<>();

        switch (user.getRole()) {
            case UserRoleEnum.ADMIN:
                jobs = jobRepository.findAll();
                break;

            case UserRoleEnum.EMPLEADO:
            case UserRoleEnum.ESPECIALISTA:
                jobs = jobAssignmentsRepo.findJobsByUserId(user.getId());
                break;

            case UserRoleEnum.CLIENTE:
                jobs = jobRepository.findByVehicleOwnerId(user.getId());
                break;

            default:
                throw new BusinessException(HttpStatus.FORBIDDEN, "Rol de usuario no permitido");
        }
        return jobs
                .stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }


    public void createNewJob(CreateJobDto createJobDto, String token){

        sessionService.isAdmin(token);

        VehicleEntity vehicle = vehicleService.getOwnerByPlate(createJobDto.getPlate());

        JobEntity jobEntity = new JobEntity(vehicle,createJobDto.getDescription(), createJobDto.getJobStatus(),null,createJobDto.getEstimatedTime(), LocalDateTime.now(),null);

        try{
            jobRepository.save(jobEntity);
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error trying save job");
        }

    }

    public ArrayList<JobDto> getJobsByStatus(String status){

        List<JobEntity> jobsByStatus = jobRepository.getAllJobsByStatus(status);
        ArrayList<JobDto> jobs = new ArrayList<>();

        jobsByStatus.forEach(jobStauts -> {
            JobDto jobDto = JobDto.builder().id(jobStauts.getId()).vehiclePlate(jobStauts.getVehicle().getPlate())
                    .description(jobStauts.getDescription()).estimatedTime(jobStauts.getEstimatedTime())
                    .status(jobStauts.getStatus().name()).build();
            jobs.add(jobDto);
        });


     return jobs;
    }



    public void cancelJob(String jobId){
        try{
            jobRepository.cancelJob(jobId);
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error canceling the job");
        }
    }


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


    public void updateJobStatus(UpdateJobStatusDto updateJobStatusDto){

        try{
            jobRepository.updateJobStatus(updateJobStatusDto.getStatus(), updateJobStatusDto.getJobId());
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"The job has not been update");
        }
    }

    public JobEntity getJobById(Integer id){
        Optional<JobEntity> optionalJobEntity = jobRepository.findById(id);

        if(optionalJobEntity.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "The job was not found");
        }

        return optionalJobEntity.get();
    }

    private JobDto fromEntity(JobEntity job) {
        return JobDto.builder()
                .id(job.getId())
                .description(job.getDescription())
                .status(job.getStatus().name())
                .vehiclePlate(job.getVehicle().getPlate())
                .build();
    }
}
