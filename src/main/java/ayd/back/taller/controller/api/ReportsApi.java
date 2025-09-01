package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.reports.JobReportRequestDto;
import ayd.back.taller.dto.response.reports.JobReportResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
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

}
