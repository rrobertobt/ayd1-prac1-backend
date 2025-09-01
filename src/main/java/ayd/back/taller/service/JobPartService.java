package ayd.back.taller.service;

import ayd.back.taller.dto.request.jobs.RegisterJobPartRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.SessionResponseDto;
import ayd.back.taller.dto.response.jobs.JobPartResponseDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.mappers.JobPartMapper;
import ayd.back.taller.repository.crud.*;
import ayd.back.taller.repository.entities.*;
import ayd.back.taller.repository.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobPartService {

    private final JobRepository jobRepository;
    private final PartRepository partRepository;
    private final PartCatalogRepository partsCatalogRepository;
    private final JobPartsRepository jobPartsRepository;
    private final JobService jobService;
    private final UserCrud userRepository;
    private final SessionService sessionService;
    private final JobPartMapper mapper;

    @Transactional(readOnly = true)
    public List<JobPartResponseDto> getJobPartsByJobId(String token, Integer jobId) {
        sessionService.validateSessionToken(token);

        // Validar existencia del job
        JobEntity job = jobRepository.findById(jobId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Job with id " + jobId + "not found"));

        // Obtener partes usadas en ese job
        List<JobPartsEntity> jobParts = jobPartsRepository.findByJob(job);

        if (jobParts.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "There are no spare parts registered for this job.");
        }

        return jobParts.stream().map(mapper::toResponse).collect(Collectors.toList());
    }


    @Transactional
    public ResponseSuccessDto registerPartUsage(Integer jobId, RegisterJobPartRequestDto dto, String token) {
        SessionResponseDto session = sessionService.validateSessionToken(token);

        JobEntity job = jobRepository.findById(jobId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Job with id " + jobId + "not found"));

        if ( !(session.getRole().equals(UserRoleEnum.EMPLEADO) || session.getRole().equals(UserRoleEnum.ESPECIALISTA)) ) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions");
        }

        UserEntity user = userRepository.getUserByEmail(session.getEmail())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "User not found"));

        jobService.validateEmployeeIsRelatedToJob(user.getId(), job);

        PartEntity part = partRepository.findById(dto.getPartId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Job with id " + jobId + "not found"));

        PartCatalogEntity catalog = partsCatalogRepository
                .findBySupplierIdIsNullAndPartId(part.getId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "The spare part is not in the workshop inventory"));

        if (catalog.getStock() < dto.getAmount()) {
            throw new BusinessException(HttpStatus.CONFLICT, "There is not enough stock for this spare part.");
        }

        catalog.setStock(catalog.getStock() - dto.getAmount());
        partsCatalogRepository.save(catalog);

        JobPartsEntity jobPart = new JobPartsEntity();
        jobPart.setJob(job);
        jobPart.setPart(part);
        jobPart.setUnitPrice(catalog.getPrice().doubleValue());
        jobPart.setAmount(dto.getAmount());

        JobPartsEntity saved = jobPartsRepository.save(jobPart);
        return ResponseSuccessDto.builder()
                .code(HttpStatus.CREATED)
                .message("Part usage registered, job_part entry created")
                .body(mapper.toResponse(saved))
                .build();
    }
}
