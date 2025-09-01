package ayd.back.taller.controller;

import ayd.back.taller.controller.api.ReportsApi;
import ayd.back.taller.dto.request.reports.JobReportRequestDto;
import ayd.back.taller.dto.request.reports.PartsUsageReportRequestDto;
import ayd.back.taller.dto.response.reports.JobReportResponseDto;
import ayd.back.taller.dto.response.reports.PartsUsageReportResponseDto;
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

    @Override
    public ResponseEntity<List<JobReportResponseDto>> jobsReport( String token, JobReportRequestDto filters) {
        log.info("GET /reports/jobs");
        List<JobReportResponseDto> resp = jobReportsService.getJobsReport(token, filters);
        return ResponseEntity.ok(resp);
    }

    @Override
    public ResponseEntity<List<PartsUsageReportResponseDto>> getPartsUsageReport(String token, PartsUsageReportRequestDto filters) {
        List<PartsUsageReportResponseDto> report = partsUsageReportService.getPartsUsageReport(token, filters);
        return ResponseEntity.ok(report);
    }


}
