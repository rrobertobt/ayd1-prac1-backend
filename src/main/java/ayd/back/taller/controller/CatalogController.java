package ayd.back.taller.controller;

import ayd.back.taller.controller.api.CatalogApi;
import ayd.back.taller.dto.request.PartCatalogRequestDto;
import ayd.back.taller.dto.response.PartCatalogResponseDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CatalogController implements CatalogApi {

    private static final Logger log = LoggerFactory.getLogger(CatalogController.class);
    private final CatalogService catalogService;

    @Override
    public ResponseEntity<List<PartCatalogResponseDto>> findCatalog() {
        log.info("GET /catalog");
        return ResponseEntity.ok(catalogService.findCatalog());
    }

    @Override
    public ResponseEntity<List<PartCatalogResponseDto>> findCatalog(Integer supplierId) {
        log.info("GET /catalog supplierId={}", supplierId);
        return ResponseEntity.ok(catalogService.findCatalog(supplierId));
    }

    @Override
    public ResponseEntity<PartCatalogResponseDto> getCatalogItem(Integer catalogId) {
        log.info("GET /catalog catalogId={}", catalogId);
        return ResponseEntity.ok(catalogService.getCatalogItem(catalogId));
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> upsertCatalogEntry(PartCatalogRequestDto dto) {
        log.info("POST /catalog");
        ResponseSuccessDto response = catalogService.upsertCatalogEntry(dto);
        return new ResponseEntity<>(response, response.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> updatePrice(Integer id, Map<String, Double> body) {
        ResponseSuccessDto response = catalogService.updateCatalogPrice(id, body.get("price"));
        return new ResponseEntity<>(response, response.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> updateStock(Integer id, Map<String, Integer> body) {
        ResponseSuccessDto response = catalogService.updateCatalogStock(id, body.get("stock"));
        return new ResponseEntity<>(response, response.getCode());
    }

}