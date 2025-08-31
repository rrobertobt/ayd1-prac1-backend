package ayd.back.taller.dto.request;


import ayd.back.taller.repository.enums.JobStatusEnum;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateJobDto {

    private String plate;

    private String description;

    private JobStatusEnum jobStatus;

    private Integer duration;

}
