package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.CreateJobDto;
import ayd.back.taller.dto.request.NewServiceTypeDto;
import ayd.back.taller.dto.request.NewSpecialtiesDto;
import ayd.back.taller.dto.request.UpdateServicePriceDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
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






}
