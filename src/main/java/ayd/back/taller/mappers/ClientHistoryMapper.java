package ayd.back.taller.mappers;

import ayd.back.taller.dto.response.reports.ClientJobHistoryResponseDto;
import ayd.back.taller.repository.entities.JobEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ClientHistoryMapper {

    public ClientJobHistoryResponseDto toResponse(JobEntity job){
        return ClientJobHistoryResponseDto.builder()
                .jobId(job.getId())
                .plate(job.getVehicle().getPlate())
                .description(job.getDescription())
                .status(job.getStatus().name())
                .createdAt(job.getCreatedAt())
                .authorizedAt(job.getAuthorizedAt())
                .build();
    }
}
