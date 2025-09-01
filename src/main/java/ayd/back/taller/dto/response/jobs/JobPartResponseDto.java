package ayd.back.taller.dto.response.jobs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class JobPartResponseDto {
    private Integer id;
    private Integer jobId;
    private Integer partId;
    private String partName;
    private Double unitPrice;
    private Integer amount;
    private LocalDateTime createdAt;
}

