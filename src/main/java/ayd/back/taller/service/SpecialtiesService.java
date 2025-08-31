package ayd.back.taller.service;

import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.SpecialtiesDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.crud.SpecialtiesRepository;
import ayd.back.taller.repository.entities.SpecialtiesEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class SpecialtiesService {

    private final SpecialtiesRepository specialtiesRepository;

    public ResponseSuccessDto createSpecialties(String name){
        SpecialtiesEntity specialtiesEntity = new SpecialtiesEntity();
        specialtiesEntity.setName(name);
        specialtiesEntity.setCreatedAt(LocalDateTime.now());
        specialtiesEntity.setUpdateAt(null);


        specialtiesRepository.save(specialtiesEntity);

        return ResponseSuccessDto.builder().code(HttpStatus.CREATED).build();
    }

    public ResponseSuccessDto getAllSpecialties(){
        List<SpecialtiesEntity> specialtiesEntities = specialtiesRepository.findAll();
        ArrayList<SpecialtiesDto> specialtiesDtos = new ArrayList<>();
        specialtiesEntities.forEach(specialites -> {
            SpecialtiesDto specialtiesDto = SpecialtiesDto.builder().id(specialites.getId()).name(specialites.getName()).build();
            specialtiesDtos.add(specialtiesDto);
        });

        return ResponseSuccessDto.builder().code(HttpStatus.OK).body(specialtiesDtos).build();
    }


    public SpecialtiesEntity getSpecialtiesForId(Integer id){
        Optional<SpecialtiesEntity> optionalSpecialtiesEntity = specialtiesRepository.findById(id);

        if(optionalSpecialtiesEntity.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "The specialties not exist");
        }

        return optionalSpecialtiesEntity.get();

    }

}
