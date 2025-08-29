package ayd.back.taller.mappers;

import ayd.back.taller.dto.response.PartCatalogResponseDto;
import ayd.back.taller.repository.entities.PartCatalogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CatalogMapper {

    public PartCatalogResponseDto toCatalogResponse(PartCatalogEntity e) {
        PartCatalogResponseDto r = new PartCatalogResponseDto();
        r.setId(e.getId());
        if (e.getSupplier() != null) {
            r.setSupplierId(e.getSupplier().getId());
        }
        r.setPartId(e.getPart().getId());
        r.setPartCode(e.getPart().getCode());
        r.setPartName(e.getPart().getName());
        r.setPrice(e.getPrice());
        r.setStock(e.getStock());
        r.setCreatedAt(e.getCreatedAt());
        r.setUpdatedAt(e.getUpdatedAt());
        return r;
    }
}
