package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.jobs.CreateJobTaskRequestDto;
import ayd.back.taller.dto.request.jobs.UpdateTaskStatusRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.jobs.JobTaskResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/jobs")
public interface JobTaskApi {

    @Operation(
            summary = "Add a new task to a job",
            description = "Creates a new job task associated with a job and a service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid job or service id"),
    })
    @PostMapping("tasks")
    ResponseEntity<ResponseSuccessDto> addTaskToJob(@RequestBody CreateJobTaskRequestDto request, @RequestHeader(value = "session-token") String token);


    @Operation(
            summary = "Get all tasks from a job",
            description = "Given a job ID, returns the list of its associated tasks."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Job not found by given ID")
    })
    @GetMapping("/{jobId}/tasks")
    ResponseEntity<List<JobTaskResponseDto>> getTasksByJobId(@PathVariable Integer jobId, @RequestHeader(value = "session-token") String token);


    @Operation(
            summary = "Get possible values for a task status",
            description = "Returns an array with all the posible status for a task."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status list obtained successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or expired session", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error, something went wrong :(", content = @Content)
    })
    @GetMapping("/tasks/status")
    ResponseEntity<List<String>> getAllTaskStatus(
            @RequestHeader("session-token") String token
    );


    @Operation(
            summary = "Update the status of a task",
            description = "Allows to change the status of an existing task given its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully",
                    content = @Content(schema = @Schema(implementation = JobTaskResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request (missing or incorrect status)", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid or expired session", content = @Content),
            @ApiResponse(responseCode = "403", description = "The user does not have permissions to modify the task", content = @Content),
            @ApiResponse(responseCode = "404", description = "The task with the provided ID was not found", content = @Content),
    })
    @PutMapping("tasks/{taskId}/status")
    ResponseEntity<ResponseSuccessDto> updateTaskStatus(
            @RequestHeader("session-token") String token,
            @PathVariable("taskId") Integer taskId,
            @RequestBody UpdateTaskStatusRequestDto request
    );

    }
