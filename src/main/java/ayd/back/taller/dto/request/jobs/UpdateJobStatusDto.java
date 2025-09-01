package ayd.back.taller.dto.request.jobs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateJobStatusDto {


    private String status;

    private Integer jobId;

}
