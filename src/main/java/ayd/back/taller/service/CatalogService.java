package ayd.back.taller.service;

import ayd.back.taller.dto.request.PartCatalogRequestDto;
import ayd.back.taller.dto.response.PartCatalogResponseDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.SessionResponseDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.mappers.CatalogMapper;
import ayd.back.taller.repository.crud.PartCatalogRepository;
import ayd.back.taller.repository.crud.PartRepository;
import ayd.back.taller.repository.crud.UserCrud;
import ayd.back.taller.repository.entities.PartCatalogEntity;
import ayd.back.taller.repository.entities.PartEntity;
import ayd.back.taller.repository.entities.UserEntity;
import ayd.back.taller.repository.enums.UserRoleEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatalogService {

    private final PartRepository partRepository;
    private final PartCatalogRepository partCatalogRepository;
    private final UserCrud userRepository;
    private final CatalogMapper catalogMapper;
    private final SessionService sessionService;

    public List<PartCatalogResponseDto> findCatalog(String token) {
        sessionService.validateSessionToken(token);

        List<PartCatalogEntity> list = partCatalogRepository.findBySupplierIdIsNull();
        return list.stream().map(catalogMapper::toCatalogResponse).collect(Collectors.toList());
    }

    public List<PartCatalogResponseDto> findCatalog(Integer supplierId, String token) {
        sessionService.validateSessionToken(token);

        List<PartCatalogEntity> list = partCatalogRepository.findBySupplierId(supplierId);
        return list.stream().map(catalogMapper::toCatalogResponse).collect(Collectors.toList());
    }

    public PartCatalogResponseDto getCatalogItem(Integer catalogId, String token) {
        sessionService.validateSessionToken(token);

        PartCatalogEntity entity = partCatalogRepository.findById(catalogId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Catalog entry not found: " + catalogId));
        return catalogMapper.toCatalogResponse(entity);
    }

    @Transactional
    public ResponseSuccessDto upsertCatalogEntry(PartCatalogRequestDto dto, String token) {
        if (dto.getPartId() == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Part id is required");
        }

        SessionResponseDto sessionDto = sessionService.validateSessionToken(token);

        // Validación de permisos
        if (dto.getSupplierId() == null) {
            if (!UserRoleEnum.ADMIN.equals(sessionDto.getRole())) {
                throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions");
            }
        } else {
            UserEntity supplier = userRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Supplier not found: " + dto.getSupplierId()));

            if (!supplier.getEmail().equals(sessionDto.getUserEmail())) {
                throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions");
            }
        }

        PartEntity part = partRepository.findById(dto.getPartId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Part not found: " + dto.getPartId()));

        if (dto.getId() != null) {
            partCatalogRepository.findById(dto.getId())
                    .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Catalog entry not found: " + dto.getId()));
        }

        // Buscar si ya existe la relación proveedor–parte
        PartCatalogEntity entity = partCatalogRepository
                .findBySupplierIdAndPartId(dto.getSupplierId(), dto.getPartId())
                .orElseGet(() -> {
                    PartCatalogEntity newEntity = new PartCatalogEntity();
                    newEntity.setCreatedAt(LocalDateTime.now());
                    return newEntity;
                });

        entity.setPart(part);
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock() == null ? 0 : dto.getStock());
        entity.setUpdatedAt(LocalDateTime.now());

        PartCatalogEntity saved = partCatalogRepository.save(entity);
        PartCatalogResponseDto resp = catalogMapper.toCatalogResponse(saved);

        boolean isNew = entity.getId() == null;
        return ResponseSuccessDto.builder()
                .code(isNew ? HttpStatus.CREATED : HttpStatus.OK)
                .message(isNew ? "Catalog entry created" : "Catalog entry updated")
                .body(resp)
                .build();
    }


    @Transactional
    public ResponseSuccessDto updateCatalogPrice(Integer catalogId, Double newPrice, String token) {
        PartCatalogEntity entity = partCatalogRepository.findById(catalogId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Catalog entry not found: " + catalogId));

        SessionResponseDto sessionDto = sessionService.validateSessionToken(token);
        UserEntity supplier = entity.getSupplier();
        if (supplier != null){
            if (!(supplier.getEmail() == sessionDto.getUserEmail())){
                throw new BusinessException(HttpStatus.FORBIDDEN,"User does not have the necessary permissions");
            }
        } else {
            if (!UserRoleEnum.ADMIN.equals(sessionDto.getRole())) {
                throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions");
            }
        }

        if (newPrice < 0) new BusinessException(HttpStatus.BAD_REQUEST, "Price can not be negative");

        entity.setPrice(newPrice);
        entity.setUpdatedAt(LocalDateTime.now());

        PartCatalogEntity saved = partCatalogRepository.save(entity);
        PartCatalogResponseDto resp = catalogMapper.toCatalogResponse(saved);

        return ResponseSuccessDto.builder()
                .code(HttpStatus.OK)
                .message("Price updated")
                .body(resp)
                .build();
    }


    @Transactional
    public ResponseSuccessDto updateCatalogStock(Integer catalogId, Integer newStock, String token) {
        PartCatalogEntity entity = partCatalogRepository.findById(catalogId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Catalog entry not found: " + catalogId));

        SessionResponseDto sessionDto = sessionService.validateSessionToken(token);
        UserEntity supplier = entity.getSupplier();
        if (supplier != null){
            if (!(supplier.getEmail() == sessionDto.getUserEmail())){
                throw new BusinessException(HttpStatus.FORBIDDEN,"User does not have the necessary permissions");
            }
        } else {
            if (!UserRoleEnum.ADMIN.equals(sessionDto.getRole())) {
                throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions");
            }
        }

        if (newStock < 0) new BusinessException(HttpStatus.BAD_REQUEST, "Stock can not be negative");

        entity.setStock(newStock);
        entity.setUpdatedAt(LocalDateTime.now());

        PartCatalogEntity saved = partCatalogRepository.save(entity);
        PartCatalogResponseDto resp = catalogMapper.toCatalogResponse(saved);

        return ResponseSuccessDto.builder()
                .code(HttpStatus.OK)
                .message("Stock updated")
                .body(resp)
                .build();
    }

}
