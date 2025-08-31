package ayd.back.taller.controller;

import ayd.back.taller.controller.api.JobLogApi;
import ayd.back.taller.dto.request.jobs.CreateJobLogRequestDto;
import ayd.back.taller.dto.response.jobs.JobLogsResponseDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.jobs.LogTypeResponseDto;
import ayd.back.taller.service.JobLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class JobLogController implements JobLogApi {

    private static final Logger log = LoggerFactory.getLogger(JobLogController.class);
    private final JobLogService jobLogService;

    @Override
    public ResponseEntity<ResponseSuccessDto> addJobLog(String token, CreateJobLogRequestDto dto) {
        log.info("POST /jobs/logs");
        var resp = jobLogService.addJobLog(dto, token);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<List<JobLogsResponseDto>> getJobLogsByJobId(String token, Integer jobId) {
        log.info("GET /jobs/{}/logs", jobId);
        return ResponseEntity.ok(jobLogService.getJobLogsByJobId(jobId, token));
    }

    @Override
    public ResponseEntity<List<String>> getLogTypes(String token) {
        log.info("GET /jobs/logs/types");
        return ResponseEntity.ok(jobLogService.getAllLogTypes(token));
    }

}
