package ayd.back.taller.dto.request.jobs;
import ayd.back.taller.repository.enums.LogTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateJobLogRequestDto {
    @NotNull(message = "Job id is required")
    private Integer jobId;

    @NotNull(message = "Log type is required")
    private LogTypeEnum logType;

    private String description;
}
