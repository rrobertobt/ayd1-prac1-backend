package ayd.back.taller.mappers;

import ayd.back.taller.dto.response.jobs.JobTaskResponseDto;
import ayd.back.taller.repository.entities.JobTaskEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JobTaskMapper {

    public JobTaskResponseDto toResponse(JobTaskEntity task){
        return JobTaskResponseDto.builder()
                .id(task.getId())
                .jobId(task.getJob().getId())
                .serviceId(task.getService().getId())
                .serviceName(task.getService().getName())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .updateAt(task.getUpdateAt())
                .build();

    }
}
