package ayd.back.taller.service;


import ayd.back.taller.dto.request.UpdateServicePriceDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.VehicleDto;
import ayd.back.taller.mappers.VehicleMapper;
import ayd.back.taller.repository.entities.ServiceTypesEntity;
import ayd.back.taller.repository.entities.VehicleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {

    private final SessionService sessionService;

    private final VehicleService vehicleService;

    private final VehicleMapper vehicleMapper;

    private final ServiceTypeService serviceTypeService;

    private final JobTaskService jobTaskService;


    public ResponseSuccessDto getAllVehicles(String sessionToken){
        sessionService.isAdmin(sessionToken);
        return vehicleService.getAllVehicles();
    }

    public ResponseSuccessDto getOwnerByVehiclePlate(String sessionToken, String plate){

        sessionService.isAdmin(sessionToken);
        VehicleEntity vehicle = vehicleService.getOwnerByPlate(plate);
        VehicleDto vehicleDto = vehicleMapper.toVehicleDtoResponse(new VehicleEntity());

        return ResponseSuccessDto.builder().code(HttpStatus.OK).body(vehicleDto).build();
    }


    public ResponseSuccessDto updateServicePrice(UpdateServicePriceDto updateServicePriceDto){
        serviceTypeService.updatePriceForServiceType(updateServicePriceDto.getNewPrice(), updateServicePriceDto.getId());
        return ResponseSuccessDto.builder().code(HttpStatus.OK).message("The service price was updated successfully").build();
    }


    public ResponseSuccessDto getJobTaskByStatus(String token){
        sessionService.validateSessionToken(token);

        return null;
    }

}
