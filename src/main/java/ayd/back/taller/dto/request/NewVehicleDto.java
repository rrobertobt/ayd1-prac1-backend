package ayd.back.taller.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewVehicleDto {

    private Integer nit;

    private String plate;

    private String vin;

    private String model;

    private Integer year;

    private String brand;

    private String color;
}
