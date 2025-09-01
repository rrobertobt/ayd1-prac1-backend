package ayd.back.taller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobAssignmentDto {

    private Integer jobId;

    private Integer userId;



}
