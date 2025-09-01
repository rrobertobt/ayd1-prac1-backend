package ayd.back.taller.controller;

import ayd.back.taller.controller.api.ReportsApi;
import ayd.back.taller.dto.request.reports.JobReportRequestDto;
import ayd.back.taller.dto.response.reports.JobReportResponseDto;
import ayd.back.taller.service.reports.JobReportsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReportsController implements ReportsApi {

    private final JobReportsService reportsService;

    @Override
    public ResponseEntity<List<JobReportResponseDto>> jobsReport( String token, JobReportRequestDto filters) {
        log.info("GET /reports/jobs", token);
        List<JobReportResponseDto> resp = reportsService.getJobsReport(token, filters);
        return ResponseEntity.ok(resp);
    }
}
