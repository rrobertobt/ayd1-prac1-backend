package ayd.back.taller.service;

import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.crud.ServiceTypesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class ServiceTypeService {

    private final ServiceTypesRepository serviceTypesRepository;


    public void updatePriceForServiceType(Double newPrice, Integer id){
        try{
            serviceTypesRepository.updatePriceForService(newPrice,id);
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error in update price for a service.");
        }
    }

    public void createServiceType(){

    }

}
