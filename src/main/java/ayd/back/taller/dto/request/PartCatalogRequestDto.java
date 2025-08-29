package ayd.back.taller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartCatalogRequestDto {
    private Integer id;
    private Integer supplierId;
    @NotNull
    private Integer partId;
    private Double price;
    private Integer stock;

}
