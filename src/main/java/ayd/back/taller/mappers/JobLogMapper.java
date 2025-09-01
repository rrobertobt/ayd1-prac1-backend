package ayd.back.taller.mappers;

import ayd.back.taller.dto.response.jobs.JobLogsResponseDto;
import ayd.back.taller.repository.entities.JobLogsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JobLogMapper {

 public JobLogsResponseDto toResponse(JobLogsEntity log){
     return JobLogsResponseDto.builder()
             .id(log.getId())
             .logType(log.getLogType().name())
             .description(log.getDescription())
             .occurredAt(log.getOccurredAt())
             .createdAt(log.getCreatedAt())
             .updatedAt(log.getUpdateAt())
             .userName(log.getUser().getName())
             .build();
 }
}
