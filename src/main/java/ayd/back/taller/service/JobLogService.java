package ayd.back.taller.service;

import ayd.back.taller.dto.request.jobs.CreateJobLogRequestDto;
import ayd.back.taller.dto.response.jobs.JobLogsResponseDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.mappers.JobLogMapper;
import ayd.back.taller.repository.crud.JobLogsRepository;
import ayd.back.taller.repository.crud.JobRepository;
import ayd.back.taller.repository.crud.UserCrud;
import ayd.back.taller.repository.entities.JobEntity;
import ayd.back.taller.repository.entities.JobLogsEntity;
import ayd.back.taller.repository.entities.UserEntity;
import ayd.back.taller.repository.enums.JobStatusEnum;
import ayd.back.taller.repository.enums.LogTypeEnum;
import ayd.back.taller.repository.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JobLogService {

    private final JobRepository jobRepository;
    private final JobLogsRepository jobLogsRepository;
    private final UserCrud userRepository;
    private final SessionService sessionService;
    private final JobService jobService;
    private final JobLogMapper mapper;

    public List<String> getAllLogTypes(String token) {
        sessionService.validateSessionToken(token);

        return Arrays.stream(LogTypeEnum.values())
                .map(Enum::name).collect(Collectors.toList());
    }


    public List<JobLogsResponseDto> getJobLogsByJobId(Integer jobId, String token) {
        sessionService.validateSessionToken(token);
        List<JobLogsEntity> logs = jobLogsRepository.findByJobId(jobId);
        return logs.stream().map(mapper::toResponse).collect(Collectors.toList());
    }


    @Transactional
    public ResponseSuccessDto addJobLog(CreateJobLogRequestDto dto, String token) {
        var session = sessionService.validateSessionToken(token);

        // Buscar usuario
        UserEntity user = userRepository.getUserByEmail(session.getEmail())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "User not found"));

        // Buscar trabajo
        JobEntity job = jobRepository.findById(dto.getJobId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Job not found"));

        if (session.getRole().equals(UserRoleEnum.PROVEEDOR)) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions");
        }

        //si es cliente, solo puede agregar log si es el dueño del vehículo del que trata el trabajo
        if (session.getRole().equals(UserRoleEnum.CLIENTE) &&
                !job.getVehicle().getOwner().getEmail().equals(session.getEmail())) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions");
        }

        //si es empleado, solo puede agregar log si está asignado al trabajo
        if ( session.getRole().equals(UserRoleEnum.EMPLEADO) || session.getRole().equals(UserRoleEnum.ESPECIALISTA) ) {
            jobService.validateEmployeeIsRelatedToJob(user.getId(), job);
        }

        if (job.getStatus().equals(JobStatusEnum.PENDIENTE)) { //el trabajo no puede estar pendiente, debe ser autorizado antes de agregar logs
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Job must be authorized before adding a log");
        }

        // Crear log
        JobLogsEntity log = new JobLogsEntity();
        log.setJob(job);
        log.setUser(user);
        log.setLogType(dto.getLogType());
        log.setDescription(dto.getDescription());
        log.setOccurredAt(LocalDateTime.now());

        JobLogsEntity saved = jobLogsRepository.save(log);

        // Actualizar estado del trabajo si es solicitud de especialista
        if (dto.getLogType() == LogTypeEnum.SOLICITUD_DE_ESPECIALISTA) {
            job.setStatus(JobStatusEnum.NECESITA_ESPECIALISTA);
            jobRepository.save(job);
        } else {
            job.setStatus(JobStatusEnum.EN_CURSO);
        }
        jobRepository.save(job);
        return ResponseSuccessDto.builder()
                .code(HttpStatus.OK)
                .message("Log added successfully")
                .body(mapper.toResponse(saved))
                .build();
    }

}
