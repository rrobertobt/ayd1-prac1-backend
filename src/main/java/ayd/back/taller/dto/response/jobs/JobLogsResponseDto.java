package ayd.back.taller.dto.response.jobs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class JobLogsResponseDto {
    private Integer id;
    private String logType;
    private String description;
    private LocalDateTime occurredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userName;
}
