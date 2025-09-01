package ayd.back.taller.controller;

import ayd.back.taller.controller.api.JobPartApi;
import ayd.back.taller.dto.request.jobs.RegisterJobPartRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.jobs.JobPartResponseDto;
import ayd.back.taller.service.JobPartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobPartsController implements JobPartApi {

    private static final Logger log = LoggerFactory.getLogger(JobPartsController.class);
    private final JobPartService jobPartService;

    @Override
    public ResponseEntity<ResponseSuccessDto> registerPartUsage( String token, Integer jobId, RegisterJobPartRequestDto dto) {
        log.info("POST /jobs/parts");
        ResponseSuccessDto response = jobPartService.registerPartUsage(jobId, dto, token);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<JobPartResponseDto>> getJobPartsUsed(String token, Integer jobId) {
        log.info("GET /jobs/parts");
        return ResponseEntity.ok(jobPartService.getJobPartsByJobId(token, jobId));
    }

}
