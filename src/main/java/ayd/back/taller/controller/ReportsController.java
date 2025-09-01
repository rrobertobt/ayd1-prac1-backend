package ayd.back.taller.controller;

import ayd.back.taller.controller.api.ReportsApi;
import ayd.back.taller.dto.request.reports.ClientJobHistoryRequestDto;
import ayd.back.taller.dto.response.reports.ClientRatingResponseDto;
import ayd.back.taller.dto.request.reports.JobReportRequestDto;
import ayd.back.taller.dto.request.reports.PartsUsageReportRequestDto;
import ayd.back.taller.dto.response.reports.ClientJobHistoryResponseDto;
import ayd.back.taller.dto.response.reports.JobReportResponseDto;
import ayd.back.taller.dto.response.reports.PartsUsageReportResponseDto;
import ayd.back.taller.service.ClientReportsService;
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
        List<ClientRatingResponseDto> ratings = clientReportsService.getUserRatings(token, userId);
        return ResponseEntity.ok(ratings);
    }


}
