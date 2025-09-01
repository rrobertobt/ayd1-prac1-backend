package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.*;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin")
public interface AdminApi {


    @GetMapping("/vehicles")
    ResponseEntity<ResponseSuccessDto> getAllVehicles(@RequestHeader(value = "session-token") String token);


    @GetMapping("/vehicle/{vehicle_plate}/owner")
    ResponseEntity<ResponseSuccessDto> GetVehicleOwner(@RequestHeader(value = "session-token") String token,
                                                       @PathVariable(value = "vehicle_plate") String plate);

    @PostMapping("/service/price")
    ResponseEntity<ResponseSuccessDto> updateServicePrice(@RequestBody UpdateServicePriceDto updateServicePriceDto,
                                                          @RequestHeader(value = "session-token") String token);

    @PostMapping("/specialties")
    ResponseEntity<ResponseSuccessDto> createSpecialties(@RequestBody NewSpecialtiesDto newSpecialtiesDto,
                                                         @RequestHeader(value = "session-token") String sessionToken);



    @PostMapping("/job")
    ResponseEntity<ResponseSuccessDto> createJob(@RequestBody CreateJobDto createJobDto,
                                                 @RequestHeader(value = "session-token") String token);



    @PostMapping("/service_type")
    ResponseEntity<ResponseSuccessDto> createServiceType(@RequestBody NewServiceTypeDto newServiceTypeDto,
                                                         @RequestHeader(value = "session-token") String token);



    @GetMapping("/job/status")
    ResponseEntity<ResponseSuccessDto> getJobByStatus(@RequestHeader(value = "sessiont-token") String token,
                                                      @RequestParam("id") String status);

    @PostMapping("/job/cancel")
    ResponseEntity<ResponseSuccessDto> cancelJob(@RequestBody CancelJobDto cancelJobDto,
                                                 @RequestHeader(value = "session-token") String token);

}
