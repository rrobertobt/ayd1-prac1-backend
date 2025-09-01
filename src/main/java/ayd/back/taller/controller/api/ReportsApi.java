package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.reports.JobReportRequestDto;
import ayd.back.taller.dto.request.reports.PartsUsageReportRequestDto;
import ayd.back.taller.dto.response.reports.JobReportResponseDto;
import ayd.back.taller.dto.response.reports.PartsUsageReportResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reports", description = "Reportes del sistema")
@RequestMapping("/reports")
public interface ReportsApi {

    @Operation(
            summary = "Report about jobs with optional filters",
            description = "Returns registered jobs by applying filters: period (startDate..endDate), license plate, status, vehicle ID, description match, employee/specialist ID involved, and whether it contains tasks of a specific specialty."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Report generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired session"),
            @ApiResponse(responseCode = "403", description = "The user does not have permissions to access the report (is not Admin)")
    })
    @GetMapping("/jobs")
    ResponseEntity<List<JobReportResponseDto>> jobsReport(
            @RequestHeader("session-token") String token,
            @ParameterObject JobReportRequestDto filters
    );


    @Operation(
            summary = "Reporte de uso de repuestos",
            description = "Devuelve un reporte del uso de repuestos con filtros por período de tiempo, tipo de repuesto, vehículo y rango de precios."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PartsUsageReportResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (fechas o precios incorrectos)", content = @Content),
            @ApiResponse(responseCode = "401", description = "Token inválido o no autorizado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/parts-usage")
    ResponseEntity<List<PartsUsageReportResponseDto>> getPartsUsageReport(
            @RequestHeader("session-token") String token,
            @ParameterObject PartsUsageReportRequestDto filters);

}
