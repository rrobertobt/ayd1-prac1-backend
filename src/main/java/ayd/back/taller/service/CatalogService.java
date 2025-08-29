package ayd.back.taller.service;

import ayd.back.taller.dto.request.PartCatalogRequestDto;
import ayd.back.taller.dto.response.PartCatalogResponseDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.mappers.CatalogMapper;
import ayd.back.taller.repository.crud.PartCatalogRepository;
import ayd.back.taller.repository.crud.PartRepository;
import ayd.back.taller.repository.crud.UserCrud;
import ayd.back.taller.repository.entities.PartCatalogEntity;
import ayd.back.taller.repository.entities.PartEntity;
import ayd.back.taller.repository.entities.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatalogService {

    private final PartRepository partRepository;
    private final PartCatalogRepository partCatalogRepository;
    private final UserCrud userRepository;
    private final CatalogMapper catalogMapper;

    public List<PartCatalogResponseDto> findCatalog() {
        //TODO: validar usuario loggeado
        List<PartCatalogEntity> list = partCatalogRepository.findBySupplierIdIsNull();
        return list.stream().map(catalogMapper::toCatalogResponse).collect(Collectors.toList());
    }

    public List<PartCatalogResponseDto> findCatalog(Integer supplierId) {
        //TODO: validar usuario loggeado
        List<PartCatalogEntity> list = partCatalogRepository.findBySupplierId(supplierId);
        return list.stream().map(catalogMapper::toCatalogResponse).collect(Collectors.toList());
    }

    public PartCatalogResponseDto getCatalogItem(Integer catalogId) {
        //TODO: validar usuario loggeado
        PartCatalogEntity entity = partCatalogRepository.findById(catalogId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Catalog entry not found: " + catalogId));
        return catalogMapper.toCatalogResponse(entity);
    }

    @Transactional
    public ResponseSuccessDto upsertCatalogEntry(PartCatalogRequestDto dto) {
        if (dto.getPartId() == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Part id is required");
        }

        //TODO: validar usuario admin o proveedor
        PartCatalogEntity entity;
        boolean isNew = (dto.getId() == null);

        if (isNew) {
            entity = new PartCatalogEntity();
            entity.setCreatedAt(LocalDateTime.now());
        } else {
            entity = partCatalogRepository.findById(dto.getId())
                    .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Catalog entry not found: " + dto.getId()));
        }

        PartEntity part = partRepository.findById(dto.getPartId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Part not found: " + dto.getPartId()));

        if (dto.getSupplierId() != null) { //si es null se asume que es del taller
            UserEntity supplier = userRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Supplier not found: " + dto.getPartId()));
            entity.setSupplier(supplier);
        }

        entity.setPart(part);
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock() == null ? 0 : dto.getStock());
        entity.setUpdatedAt(LocalDateTime.now());

        PartCatalogEntity saved = partCatalogRepository.save(entity);
        PartCatalogResponseDto resp = catalogMapper.toCatalogResponse(saved);

        return ResponseSuccessDto.builder()
                .code(isNew ? HttpStatus.CREATED : HttpStatus.OK)
                .message(isNew ? "Catalog entry created" : "Catalog entry updated")
                .body(resp)
                .build();
    }

    @Transactional
    public ResponseSuccessDto updateCatalogPrice(Integer catalogId, Double newPrice) {
        PartCatalogEntity entity = partCatalogRepository.findById(catalogId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Catalog entry not found: " + catalogId));

        if (newPrice < 0) new BusinessException(HttpStatus.BAD_REQUEST, "Price can not be negative");

        entity.setPrice(newPrice);
        entity.setUpdatedAt(LocalDateTime.now());
        partCatalogRepository.save(entity);

        return ResponseSuccessDto.builder()
                .code(HttpStatus.OK)
                .message("Price updated")
                .body(Map.of(
                        "catalogId", entity.getId(),
                        "partId", entity.getPart().getId(),
                        "supplierId", entity.getSupplier().getId(),
                        "price", entity.getPrice()
                ))
                .build();
    }


    @Transactional
    public ResponseSuccessDto updateCatalogStock(Integer catalogId, Integer newStock) {
        PartCatalogEntity entity = partCatalogRepository.findById(catalogId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Catalog entry not found: " + catalogId));

        if (newStock < 0) new BusinessException(HttpStatus.BAD_REQUEST, "Stock can not be negative");

        entity.setStock(newStock);
        entity.setUpdatedAt(LocalDateTime.now());
        partCatalogRepository.save(entity);

        return ResponseSuccessDto.builder()
                .code(HttpStatus.OK)
                .message("Stock updated")
                .body(Map.of(
                        "catalogId", entity.getId(),
                        "partId", entity.getPart().getId(),
                        "supplierId", entity.getSupplier().getId(),
                        "stock", entity.getStock()
                ))
                .build();
    }


}
