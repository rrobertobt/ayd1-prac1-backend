package ayd.back.taller.service;

import ayd.back.taller.dto.request.NewServiceTypeDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.crud.ServiceTypesRepository;
import ayd.back.taller.repository.entities.ServiceTypesEntity;
import ayd.back.taller.repository.entities.SpecialtiesEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@RequiredArgsConstructor
@Service
public class ServiceTypeService {

    private final ServiceTypesRepository serviceTypesRepository;

    private final SpecialtiesService specialtiesService;


    public void updatePriceForServiceType(Double newPrice, Integer id){
        try{
            serviceTypesRepository.updatePriceForService(newPrice,id);
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error in update price for a service.");
        }
    }

    public void createServiceType(NewServiceTypeDto newServiceTypeDto){
        ServiceTypesEntity serviceTypesEntity = new ServiceTypesEntity();

        SpecialtiesEntity specialtiesEntity = specialtiesService.getSpecialtiesForId(newServiceTypeDto.getSpecialitiesId());

        serviceTypesEntity.setSpecialty(specialtiesEntity);
        serviceTypesEntity.setName(newServiceTypeDto.getName());
        serviceTypesEntity.setDescription(newServiceTypeDto.getDescription());
        serviceTypesEntity.setPrice(newServiceTypeDto.getPrice());
        serviceTypesEntity.setEstimatedTime(newServiceTypeDto.getEstimatedTime());
        serviceTypesEntity.setCreatedAt(LocalDateTime.now());
        serviceTypesEntity.setUpdateAt(LocalDateTime.now());

        try {
            serviceTypesRepository.save(serviceTypesEntity);
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error saving service type entity");
        }

    }

}
