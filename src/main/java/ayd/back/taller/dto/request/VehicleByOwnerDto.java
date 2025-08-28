package ayd.back.taller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VehicleByOwnerDto {


    private Integer nit;

    private String plate;

}
