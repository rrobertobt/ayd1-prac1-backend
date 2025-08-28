package ayd.back.taller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
public class VehicleDto {

    private Integer owner;

    private String plate;

    private String vin;

    private String model;

    private Integer year;

    private String brand;

    private String color;

    private LocalDateTime createdAt;

}
