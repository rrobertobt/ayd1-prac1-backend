package ayd.back.taller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PartRequestDto {
    private Integer id;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String brand;
    private String description;
}
