package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.NewVehicleDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/vehicle")
public interface VehicleApi {


    @PostMapping
    ResponseEntity<ResponseSuccessDto> createNewVehicle(@RequestBody NewVehicleDto newVehicleDto, @RequestHeader(value = "session-token") String token);

    @GetMapping("/{plate}")
    ResponseEntity<ResponseSuccessDto> getVehicleByOwner(@PathVariable String plate, @RequestHeader(name = "user-nit") Integer nit);


}
