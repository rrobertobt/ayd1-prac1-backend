package ayd.back.taller.controller;

import ayd.back.taller.controller.api.PartApi;
import ayd.back.taller.dto.request.PartRequestDto;
import ayd.back.taller.dto.response.PartResponseDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.service.PartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PartController implements PartApi {

    private static final Logger log = LoggerFactory.getLogger(PartController.class);
    private final PartService partService;

    @Override
    public ResponseEntity<List<PartResponseDto>> findAllParts(String token) {
        log.info("GET /part");
        List<PartResponseDto> parts = partService.getAllParts(token);
        return ResponseEntity.ok(parts);
    }

    @Override
    public ResponseEntity<PartResponseDto> findPartById(Integer id, String token) {
        log.info("GET /part/{}", id);
        PartResponseDto dto = partService.getPartById(id, token);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> createPart(PartRequestDto dto, String token) {
        log.info("POST /part");
        ResponseSuccessDto response = partService.createPart(dto, token);
        return new ResponseEntity<>(response, response.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> updatePart(Integer id, PartRequestDto dto, String token) {
        log.info("PUT /part/{}", id);
        ResponseSuccessDto response = partService.updatePart(id, dto, token);
        return ResponseEntity.ok(response);
    }

}
