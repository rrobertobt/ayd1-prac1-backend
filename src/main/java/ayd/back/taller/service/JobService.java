package ayd.back.taller.service;

import ayd.back.taller.dto.request.CreateJobDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.crud.JobRepository;
import ayd.back.taller.repository.entities.JobEntity;
import ayd.back.taller.repository.entities.VehicleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;


@Slf4j
@RequiredArgsConstructor
@Service
public class JobService {


    private final JobRepository jobRepository;

    private final VehicleService vehicleService;

    private final SessionService sessionService;


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


}
