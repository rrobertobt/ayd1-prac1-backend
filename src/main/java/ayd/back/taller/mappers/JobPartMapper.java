package ayd.back.taller.mappers;

import ayd.back.taller.dto.response.jobs.JobPartResponseDto;
import ayd.back.taller.repository.entities.JobPartsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JobPartMapper {

    public JobPartResponseDto toResponse(JobPartsEntity jobPart){
        return JobPartResponseDto.builder()
                .id(jobPart.getId())
                .jobId(jobPart.getJob().getId())
                .partId(jobPart.getPart().getId())
                .partName(jobPart.getPart().getName())
                .unitPrice(jobPart.getUnitPrice())
                .createdAt(jobPart.getCreatedAt())
                .amount(jobPart.getAmount())
                .build();

    }
}
