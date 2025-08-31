package ayd.back.taller.controller;

import ayd.back.taller.controller.api.AdminApi;
import ayd.back.taller.dto.request.UpdateServicePriceDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.service.AdminService;
import ayd.back.taller.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.function.SelfRenderingSqmOrderedSetAggregateFunction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AdminController implements AdminApi {


    private final AdminService adminService;

    private final SessionService sessionService;

    @Override
    public ResponseEntity<ResponseSuccessDto> getAllVehicles(String token) {
        log.info("GET /admin/vehicles");
        ResponseSuccessDto responseSuccessDto = adminService.getAllVehicles(token);
        return new ResponseEntity<>(responseSuccessDto, responseSuccessDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> GetVehicleOwner(String token, String plate) {
        log.info("GERT admin/vehicle/{id}/owner");
        ResponseSuccessDto responseSuccessDto = adminService.getOwnerByVehiclePlate(token, plate);
        return new ResponseEntity<>(responseSuccessDto, responseSuccessDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> updateServicePrice(UpdateServicePriceDto updateServicePriceDto, String token) {
        sessionService.validateSessionToken(token);
        ResponseSuccessDto responseSuccessDto = adminService.updateServicePrice(updateServicePriceDto);
        return new ResponseEntity<>(responseSuccessDto,responseSuccessDto.getCode());
    }
}
