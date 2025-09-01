package ayd.back.taller.dto.request.jobs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobTaskRequestDto {
    private Integer jobId;
    private Integer serviceId;
}
