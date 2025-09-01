package ayd.back.taller.dto.request.reports;

import ayd.back.taller.repository.enums.JobStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@ParameterObject
public class JobReportRequestDto {

    @Schema(description = "Fecha inicial del reporte (yyyy-MM-dd)", example = "2025-08-01", required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Schema(description = "Fecha final del reporte (yyyy-MM-dd)", example = "2025-08-31", required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Schema(description = "Placa del vehículo a filtrar", example = "P011KKK", required = false)
    private String plate;

    @Schema(description = "ID del vehículo", example = "115", required = false)
    Integer vehicleId;

    @Schema(description = "Estado de la orden de trabajo", example = "COMPLETED", required = false)
    private JobStatusEnum status;

    @Schema(description = "Palabra(s) a buscar en la descripción", example = "revisión de rútina", required = false)
    private String q;

    @Schema(description = "ID de un empleado/especialista involucrado", example = "7", required = false)
    private Integer userId;

    @Schema(description = "ID de specialty; devuelve sólo trabajos que tengan alguna tarea de esta specialty", example = "2", required = false)
    private Integer specialtyId;

}
