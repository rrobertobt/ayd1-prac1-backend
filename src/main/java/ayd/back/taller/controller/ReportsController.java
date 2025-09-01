package ayd.back.taller.controller;

import ayd.back.taller.controller.api.ReportsApi;
import ayd.back.taller.dto.request.reports.ClientJobHistoryRequestDto;
import ayd.back.taller.dto.request.reports.IncomeExpenseReportRequestDto;
import ayd.back.taller.dto.response.reports.*;
import ayd.back.taller.dto.request.reports.JobReportRequestDto;
import ayd.back.taller.dto.request.reports.PartsUsageReportRequestDto;
import ayd.back.taller.service.reports.ClientReportsService;
import ayd.back.taller.service.reports.FinancialReportsService;
import ayd.back.taller.service.reports.JobReportsService;
import ayd.back.taller.service.reports.PartsUsageReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReportsController implements ReportsApi {

    private final JobReportsService jobReportsService;
    private final PartsUsageReportService partsUsageReportService;
    private final ClientReportsService clientReportsService;
    private final FinancialReportsService financialReportsService;

    @Override
    public ResponseEntity<List<JobReportResponseDto>> jobsReport( String token, JobReportRequestDto filters) {
        log.info("GET /reports/jobs");
        List<JobReportResponseDto> resp = jobReportsService.getJobsReport(token, filters);
        return ResponseEntity.ok(resp);
    }

    @Override
    public ResponseEntity<List<PartsUsageReportResponseDto>> getPartsUsageReport(String token, PartsUsageReportRequestDto filters) {
        log.info("GET /reports/parts-usage");
        List<PartsUsageReportResponseDto> report = partsUsageReportService.getPartsUsageReport(token, filters);
        return ResponseEntity.ok(report);
    }

    @Override
    public ResponseEntity<List<ClientJobHistoryResponseDto>> getClientJobHistory(String token, ClientJobHistoryRequestDto request) {
        log.info("GET /reports/client-jobs");
        List<ClientJobHistoryResponseDto> response = clientReportsService.getClientJobHistory(token, request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<ClientRatingResponseDto>> getUserRatings(String token, Integer userId) {
        log.info("GET /reports/client-rating");
        List<ClientRatingResponseDto> ratings = clientReportsService.getUserRatings(token, userId);
        return ResponseEntity.ok(ratings);
    }

    @Override
    public ResponseEntity<IncomeExpenseReportResponseDto> cashFlowReport(String token, IncomeExpenseReportRequestDto dto) {
        log.info("GET /reports/cashflow");
        var resp = financialReportsService.generateCashflowReport(token, dto);
        return ResponseEntity.ok(resp);
    }


}
