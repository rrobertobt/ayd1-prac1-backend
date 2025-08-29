package ayd.back.taller.service;


import ayd.back.taller.dto.request.NewVehicleDto;
import ayd.back.taller.dto.request.VehicleByOwnerDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.VehicleDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.mappers.VehicleMapper;
import ayd.back.taller.repository.crud.VehicleRepository;
import ayd.back.taller.repository.entities.UserEntity;
import ayd.back.taller.repository.entities.VehicleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserService userService;
    private final VehicleMapper mapper;

    public ResponseSuccessDto createNewVehicle(NewVehicleDto newVehicleDto){

        Optional<VehicleEntity> optionalVehicleEntity = vehicleRepository.findByVinOrPlate(newVehicleDto.getVin(), newVehicleDto.getPlate());

        if(!optionalVehicleEntity.isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST, "The vehicle with vin or plate already exist.");
        }

        UserEntity user = userService.getUserByNit(newVehicleDto.getNit());
        VehicleEntity vehicle = mapper.toVehicleEntity(newVehicleDto, user);

        vehicleRepository.save(vehicle);
        return ResponseSuccessDto.builder().code(HttpStatus.CREATED).message("The vehicle was created successfully").build();
    }

    public ResponseSuccessDto getVehicleByOwner(VehicleByOwnerDto vehicleByOwnerDto){
        UserEntity user = userService.getUserByNit(vehicleByOwnerDto.getNit());

        Optional<VehicleEntity> optionalVehicle = vehicleRepository.findByOwner(user.getId(), vehicleByOwnerDto.getPlate());

        if(optionalVehicle.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Vehicle nor found, check de owner id or plate");
        }

        VehicleDto vehicleDto = mapper.toVehicleDtoResponse(optionalVehicle.get());

        if(!vehicleDto.getOwner().equals(vehicleByOwnerDto.getNit())){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"the vehicle and the user are not related.");
        }

        return ResponseSuccessDto.builder().code(HttpStatus.OK).message("The vehicle was found").body(vehicleDto).build();
    }


}
