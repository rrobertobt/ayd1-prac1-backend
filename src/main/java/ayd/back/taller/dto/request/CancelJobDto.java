package ayd.back.taller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelJobDto {

    @JsonProperty("job_id")
    private String jobId;


}
