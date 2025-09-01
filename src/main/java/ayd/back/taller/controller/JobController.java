package ayd.back.taller.controller;

import ayd.back.taller.controller.api.JobApi;
import ayd.back.taller.dto.response.JobDto;
import ayd.back.taller.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobController implements JobApi {

    private final JobService jobService;

    @Override
    public ResponseEntity<List<JobDto>> getJobsForUser(String token) {
        List<JobDto> jobs = jobService.getJobsForUser(token);
        return ResponseEntity.ok(jobs);
    }
}
