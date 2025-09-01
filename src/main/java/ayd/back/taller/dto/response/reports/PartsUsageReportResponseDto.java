package ayd.back.taller.dto.response.reports;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PartsUsageReportResponseDto {

    @Schema(description = "ID del repuesto", example = "5")
    private Integer partId;

    @Schema(description = "Nombre del repuesto", example = "Filtro de aceite")
    private String partName;

    @Schema(description = "Cantidad usada")
    private Integer amount;

    @Schema(description = "Precio unitario promedio")
    private Double avgUnitPrice;

    @Schema(description = "Gasto total en el repuesto")
    private Double totalCost;
}
