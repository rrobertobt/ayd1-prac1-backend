package ayd.back.taller.controller;

import ayd.back.taller.controller.api.JobTaskApi;
import ayd.back.taller.dto.request.jobs.CreateJobTaskRequestDto;
import ayd.back.taller.dto.request.jobs.UpdateTaskStatusRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.jobs.JobTaskResponseDto;
import ayd.back.taller.service.JobTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobTaskController implements JobTaskApi {

    private static final Logger log = LoggerFactory.getLogger(JobTaskController.class);
    private final JobTaskService jobTaskService;

    public JobTaskController(JobTaskService jobTaskService) {
        this.jobTaskService = jobTaskService;
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> addTaskToJob(@RequestBody CreateJobTaskRequestDto request, String token) {
        log.info("POST /jobs/tasks");
        ResponseSuccessDto response = jobTaskService.addTaskToJob(request, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<List<JobTaskResponseDto>> getTasksByJobId(Integer jobId, String token) {
        log.info("GET /tasks/{}/logs", jobId);
        return ResponseEntity.ok(jobTaskService.getTasksByJobId(jobId, token));
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> updateTaskStatus(
            String token,
            Integer taskId,
            UpdateTaskStatusRequestDto request
    ) {
        log.info("PUT /jobs/tasks/{id}/status");
        ResponseSuccessDto response = jobTaskService.updateTaskStatus(token, taskId, request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<String>> getAllTaskStatus(String token) {
        log.info("GET /jobs/tasks/status");
        return ResponseEntity.ok(jobTaskService.getAllTaskStatus(token));
    }
}
