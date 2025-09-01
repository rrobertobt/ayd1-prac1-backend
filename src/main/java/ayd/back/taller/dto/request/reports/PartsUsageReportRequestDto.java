package ayd.back.taller.dto.request.reports;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@ParameterObject
public class PartsUsageReportRequestDto {

    @Schema(description = "Fecha de inicio del período a consultar", example = "2025-01-01", required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Schema(description = "Fecha de fin del período a consultar", example = "2025-01-31", required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Schema(description = "ID del tipo de repuesto a filtrar", example = "3", required = false)
    private Integer partTypeId;

    @Schema(description = "Marca del vehículo", example = "Toyota", required = false)
    private String brand;

    @Schema(description = "Modelo del vehículo", example = "Corolla", required = false)
    private String model;

    @Schema(description = "Año del vehículo", example = "2020", required = false)
    private Integer year;

    @Schema(description = "Precio mínimo del repuesto", example = "100.00", required = false)
    private Double minPrice;

    @Schema(description = "Precio máximo del repuesto", example = "1000.00", required = false)
    private Double maxPrice;
}
