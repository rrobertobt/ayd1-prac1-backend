package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.jobs.RegisterJobPartRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.jobs.JobPartResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/jobs")
public interface JobPartApi {

    @Operation(
            summary = "Register the use of a part in a job",
            description = "Creates a new job_part that associates a job and the used part")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid job or part id"),
            @ApiResponse(responseCode = "409", description = "Not enough stock of the part in the inventory"),
            @ApiResponse(responseCode = "403", description = "User doesn't have the necessary permissions, is not related to the job or the role is not employee or specialist")
    })
    @PostMapping("/{jobId}/parts")
    ResponseEntity<ResponseSuccessDto> registerPartUsage(
            @RequestHeader(value = "session-token") String token,
            @PathVariable Integer jobId,
            @RequestBody RegisterJobPartRequestDto dto
    );


    @Operation(
            summary = "Obtaining spare part usages from a job",
            description = "Returns a list of all spare parts used in a given job ID. Requires token authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spare parts list used in the job successfully obtained"),
            @ApiResponse(responseCode = "400", description = "The job ID is not valid"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Invalid or expired session"),
            @ApiResponse(responseCode = "404", description = "Job not found or no spare parts registered")
    })
    @GetMapping("/{jobId}/parts")
    ResponseEntity<List<JobPartResponseDto>> getJobPartsUsed(
            @RequestHeader("session-token") String token,
            @PathVariable("jobId") Integer jobId
    );
}
