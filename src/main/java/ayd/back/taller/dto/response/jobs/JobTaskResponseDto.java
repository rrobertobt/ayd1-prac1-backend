package ayd.back.taller.dto.response.jobs;

import ayd.back.taller.repository.enums.TaskStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class JobTaskResponseDto {
    private Integer id;
    private Integer jobId;
    private Integer serviceId;
    private String serviceName;
    private TaskStatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}

