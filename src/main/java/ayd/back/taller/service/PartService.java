package ayd.back.taller.service;

import ayd.back.taller.dto.request.PartRequestDto;
import ayd.back.taller.dto.response.PartResponseDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.mappers.PartMapper;
import ayd.back.taller.repository.crud.PartRepository;
import ayd.back.taller.repository.entities.PartEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PartService {

    private final PartRepository partRepository;
    private final PartMapper partMapper;

    public List<PartResponseDto> getAllParts() {
        //TODO: validar usuario loggeado
        return partRepository.findAll()
                .stream()
                .map(partMapper::toPartResponse)
                .collect(Collectors.toList());
    }

    public PartResponseDto getPartById(Integer id) {
        //TODO: validar usuario loggeado
        PartEntity e = partRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Part not found: " + id));
        return partMapper.toPartResponse(e);
    }

    public ResponseSuccessDto createPart(PartRequestDto dto) {
        //TODO: validar usuario admin
        validateRequiredFields(dto);

        PartEntity e = new PartEntity();
        e.setCode(dto.getCode());
        e.setName(dto.getName());
        e.setBrand(dto.getBrand());
        e.setDescription(dto.getDescription());
        e.setCreatedAt(LocalDateTime.now());
        e.setUpdatedAt(LocalDateTime.now());

        PartEntity saved = partRepository.save(e);
        PartResponseDto response = partMapper.toPartResponse(saved);
        return ResponseSuccessDto.builder()
                .code(HttpStatus.CREATED)
                .message("Part saved")
                .body(response)
                .build();
    }

    private void validateRequiredFields(PartRequestDto dto) {
        if (dto.getCode() == null || dto.getCode().isBlank()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Part code is required");
        }
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Part name is required");
        }
        if (partRepository.existsByCode(dto.getCode())) {
            throw new BusinessException(HttpStatus.CONFLICT, "Part with code " + dto.getCode() + " already exists");
        }
    }

    public ResponseSuccessDto updatePart(Integer id, PartRequestDto dto) {
        //TODO: validar usuario admin
        validateRequiredFields(dto);
        if (!partRepository.existsById(id)){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Part not found");
        }

        PartEntity e = partRepository.findById(id).get();
        e.setCode(dto.getCode());
        e.setName(dto.getName());
        e.setBrand(dto.getBrand());
        e.setDescription(dto.getDescription());
        e.setUpdatedAt(LocalDateTime.now());

        PartEntity saved = partRepository.save(e);
        PartResponseDto response = partMapper.toPartResponse(saved);

        return ResponseSuccessDto.builder()
                .code(HttpStatus.OK)
                .message("Part updated")
                .body(response)
                .build();
    }

}
