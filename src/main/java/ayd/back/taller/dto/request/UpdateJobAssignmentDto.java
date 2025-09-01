package ayd.back.taller.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateJobAssignmentDto {

    private Integer userId;

    private Integer newUserId;

    private Integer jobId;

}
