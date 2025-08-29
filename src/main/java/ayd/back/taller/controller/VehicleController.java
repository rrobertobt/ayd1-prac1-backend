package ayd.back.taller.controller;

import ayd.back.taller.controller.api.VehicleApi;
import ayd.back.taller.dto.request.NewVehicleDto;
import ayd.back.taller.dto.request.VehicleByOwnerDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RequiredArgsConstructor
@RestController
public class VehicleController implements VehicleApi {

    private final VehicleService vehicleService;


    @Override
    public ResponseEntity<ResponseSuccessDto> createNewVehicle(NewVehicleDto newVehicleDto) {
        log.info("POST /vehicle");
        ResponseSuccessDto responseSuccessDto = vehicleService.createNewVehicle(newVehicleDto);
        return new ResponseEntity<>(responseSuccessDto, responseSuccessDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> getVehicleByOwner(String plate, Integer nit) {
        log.info("GET /vehicle/plate");
        ResponseSuccessDto responseSuccessDto = vehicleService.getVehicleByOwner(VehicleByOwnerDto.builder().plate(plate).nit(nit).build());
        return new ResponseEntity<>(responseSuccessDto,responseSuccessDto.getCode());
    }

    //TODO pendiente crear el endpoint para la consulta del vehículo por parte de un admin, especialista, etc.
}
