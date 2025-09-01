package ayd.back.taller.service;

import ayd.back.taller.dto.request.jobs.CreateJobTaskRequestDto;
import ayd.back.taller.dto.request.jobs.UpdateTaskStatusRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.SessionResponseDto;
import ayd.back.taller.dto.response.jobs.JobTaskResponseDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.mappers.JobTaskMapper;
import ayd.back.taller.repository.crud.*;
import ayd.back.taller.repository.entities.*;
import ayd.back.taller.repository.enums.TaskStatusEnum;
import ayd.back.taller.repository.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class JobTaskService {

    private final JobTaskRepository jobTaskRepository;
    private final JobRepository jobRepository;
    private final ServiceTypesRepository serviceTypesRepository;
    private final SessionService sessionService;
    private final UserCrud userRepo;
    private final JobAssignmentsRepository jobAssignmentsRepo;
    private final JobTaskMapper mapper;

    public JobTaskEntity getJobByStatus(TaskStatusEnum taskStatusEnum){
        Optional<JobTaskEntity> optionalJobTaskEntity = jobTaskRepository.getJobByStatus(taskStatusEnum.getStatus());

        if(optionalJobTaskEntity.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Job task not found");
        }

        return optionalJobTaskEntity.get();
    }


    public List<String> getAllTaskStatus(String token) {
        sessionService.validateSessionToken(token);

        return Arrays.stream(TaskStatusEnum.values())
                .map(enumValue -> enumValue.name()).collect(Collectors.toList());
    }

    public List<JobTaskResponseDto> getTasksByJobId(Integer jobId, String token) {
        sessionService.validateSessionToken(token);

        JobEntity job = jobRepository.findById(jobId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Job with id " + jobId + " not found"));

        List<JobTaskEntity> tasks = jobTaskRepository.findByJob(job);

        return tasks.stream().map(mapper::toResponse).collect(Collectors.toList());
    }


    @Transactional
    public ResponseSuccessDto addTaskToJob(CreateJobTaskRequestDto dto, String token) {
        JobEntity job = jobRepository.findById(dto.getJobId())
                .orElseThrow(() -> new IllegalArgumentException("Job not found with id " + dto.getJobId()));

        validateUserPermissions(token, job);

        ServiceTypesEntity service = serviceTypesRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Service not found with id " + dto.getServiceId()));

        JobTaskEntity task = new JobTaskEntity();
        task.setJob(job);
        task.setService(service);
        task.setStatus(TaskStatusEnum.PENDIENTE);
        task.setCreatedAt(LocalDateTime.now());

        JobTaskEntity saved = jobTaskRepository.save(task);

        return ResponseSuccessDto.builder()
                .code(HttpStatus.CREATED)
                .message("Task created" )
                .body(mapper.toResponse(saved))
                .build();
    }


    @Transactional
    public ResponseSuccessDto updateTaskStatus(String token, Integer taskId, UpdateTaskStatusRequestDto dto) {
        JobTaskEntity task = jobTaskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "No se encontró la tarea con ID: " + taskId));

        validateUserPermissions(token, task.getJob());

        task.setStatus(dto.getNewStatus());
        task.setUpdateAt(java.time.LocalDateTime.now());
        JobTaskEntity saved = jobTaskRepository.save(task);

        return ResponseSuccessDto.builder()
                .code(HttpStatus.OK)
                .message("Task updated" )
                .body(mapper.toResponse(saved))
                .build();
    }

    private void validateUserPermissions(String token, JobEntity job){
        SessionResponseDto session = sessionService.validateSessionToken(token);

        // Buscar usuario
        UserEntity user = userRepo.getUserByEmail(session.getEmail())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "User not found"));

        if (session.getRole().equals(UserRoleEnum.PROVEEDOR) || session.getRole().equals(UserRoleEnum.CLIENTE)) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions");
        }

        //si es empleado, solo puede agregar tarea si está asignado al trabajo
        if ( session.getRole().equals(UserRoleEnum.EMPLEADO) || session.getRole().equals(UserRoleEnum.ESPECIALISTA) ) {
            List<JobAssignmentsEntity> jobAssignments = jobAssignmentsRepo.findByJob(job);
            boolean userAssigned = false;
            for (JobAssignmentsEntity j: jobAssignments) {
                userAssigned = userAssigned || (user.getId() == j.getUser().getId());
            }
            if (!userAssigned) {
                throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions");
            }
        }
    }
}
