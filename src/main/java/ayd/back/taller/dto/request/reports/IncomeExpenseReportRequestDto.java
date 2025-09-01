package ayd.back.taller.dto.request.reports;

import ayd.back.taller.repository.enums.FinancialReportType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@ParameterObject
public class IncomeExpenseReportRequestDto {
    @Schema(description = "Fecha inicio (yyyy-MM-dd). Opcional", example = "2025-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Schema(description = "Fecha fin (yyyy-MM-dd). Opcional", example = "2025-01-31")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Schema(description = "Tipo de reporte: INCOME, EXPENSE, BOTH", example = "BOTH")
    private FinancialReportType type = FinancialReportType.BOTH;
}
