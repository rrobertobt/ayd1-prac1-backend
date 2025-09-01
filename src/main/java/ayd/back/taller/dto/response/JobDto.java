package ayd.back.taller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobDto {

    private Integer id;

    @JsonProperty("vehicle_plate")
    private String vehiclePlate;

    private String description;

    @JsonProperty("estimated_time")
    private Integer estimatedTime;

    private String status;
}

