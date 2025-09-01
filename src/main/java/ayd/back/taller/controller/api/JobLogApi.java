package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.jobs.CreateJobLogRequestDto;
import ayd.back.taller.dto.response.jobs.JobLogsResponseDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.jobs.LogTypeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/jobs")
public interface JobLogApi {
    @Operation(
            summary = "Get possible log types",
            description = "Returns an array with all the log types defined in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Log types obtained successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or expired session", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error, something went wrong :(", content = @Content)
    })
    @GetMapping("/logs/types")
    ResponseEntity<List<String>> getLogTypes(
            @RequestHeader("session-token") String token
    );


    @Operation(
            summary = "Get all records of a job given its id",
            description = "Returns the list of all logs recorded for a job based on the job id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logs obtained successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or expired session", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error, something went wrong :(", content = @Content)
    })
    @GetMapping("/{jobId}/logs")
    ResponseEntity<List<JobLogsResponseDto>> getJobLogsByJobId(
            @RequestHeader("session-token") String token,
            @PathVariable Integer jobId);


    @Operation(
            summary = "Add a log to a job",
            description = "The employees/specialists working on a job, the admin or the owner of the vehicle register an event for the job"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Log added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or job not in correct status"),
            @ApiResponse(responseCode = "404", description = "Job or user not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or expired session", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden. User is not allowed to add a log to the job"),
            @ApiResponse(responseCode = "500", description = "Internal server error, something went wrong :(", content = @Content)
    })
    @PostMapping("/logs")
    ResponseEntity<ResponseSuccessDto> addJobLog(
            @RequestHeader("session-token") String token,
            @Valid @RequestBody CreateJobLogRequestDto dto);


}
