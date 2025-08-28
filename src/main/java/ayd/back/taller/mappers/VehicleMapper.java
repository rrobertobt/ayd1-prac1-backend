package ayd.back.taller.mappers;

import ayd.back.taller.dto.request.NewVehicleDto;
import ayd.back.taller.dto.response.VehicleDto;
import ayd.back.taller.repository.entities.UserEntity;
import ayd.back.taller.repository.entities.VehicleEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VehicleMapper {


    public VehicleEntity toVehicleEntity(NewVehicleDto vehicleDto, UserEntity owner){

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setOwner(owner);
        vehicle.setPlate(vehicleDto.getPlate());
        vehicle.setBrand(vehicleDto.getBrand());
        vehicle.setColor(vehicleDto.getColor());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setVin(vehicleDto.getVin());
        vehicle.setUpdateAt(LocalDateTime.now());

        return vehicle;
    }


    public VehicleDto toVehicleDtoResponse(VehicleEntity vehicle){

        return VehicleDto.builder()
                .owner(vehicle.getOwner().getNit())
                .plate(vehicle.getPlate())
                .vin(vehicle.getVin())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .brand(vehicle.getBrand())
                .color(vehicle.getColor())
                .build();
    }
}
