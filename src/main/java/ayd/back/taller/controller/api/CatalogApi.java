package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.PartCatalogRequestDto;
import ayd.back.taller.dto.response.PartCatalogResponseDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/catalog")
public interface CatalogApi {

    @GetMapping()
    ResponseEntity<List<PartCatalogResponseDto>> findCatalog();

    @GetMapping("/supplier/{supplierId}")
    ResponseEntity<List<PartCatalogResponseDto>> findCatalog(@PathVariable Integer supplierId);

    @GetMapping("/{catalogId}")
    ResponseEntity<PartCatalogResponseDto> getCatalogItem(@PathVariable Integer catalogId);

    @PostMapping()
    ResponseEntity<ResponseSuccessDto> upsertCatalogEntry(@RequestBody PartCatalogRequestDto dto);

    @PutMapping("/{id}/price")
    ResponseEntity<ResponseSuccessDto> updatePrice(@PathVariable Integer id, @RequestBody Map<String, Double> body);

    @PutMapping("/{id}/stock")
    ResponseEntity<ResponseSuccessDto> updateStock(@PathVariable Integer id, @RequestBody Map<String, Integer> body);

}
