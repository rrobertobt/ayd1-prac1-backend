package ayd.back.taller.dto.request;


import ayd.back.taller.repository.enums.JobStatusEnum;
import lombok.*;

import java.time.Duration;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateJobDto {

    private String plate;

    private String description;

    private JobStatusEnum jobStatus;

    //estimated time
    private Integer estimatedTime;



}
