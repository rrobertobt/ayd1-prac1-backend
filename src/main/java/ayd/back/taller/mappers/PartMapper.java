package ayd.back.taller.mappers;

import ayd.back.taller.dto.response.PartResponseDto;
import ayd.back.taller.repository.entities.PartEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PartMapper {

    public PartResponseDto toPartResponse(PartEntity e) {
        PartResponseDto r = new PartResponseDto();
        r.setId(Integer.valueOf(e.getId()));
        r.setCode(e.getCode());
        r.setName(e.getName());
        r.setBrand(e.getBrand());
        r.setDescription(e.getDescription());
        return r;
    }

}
