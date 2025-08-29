package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.PartRequestDto;
import ayd.back.taller.dto.response.PartResponseDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/part")
public interface PartApi {

    @GetMapping
    ResponseEntity<List<PartResponseDto>> findAllParts();

    @GetMapping("/{id}")
    ResponseEntity<PartResponseDto> findPartById(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<ResponseSuccessDto> createPart(@RequestBody PartRequestDto dto);

    @PutMapping("/{id}")
    ResponseEntity<ResponseSuccessDto> updatePart(@PathVariable Integer id, @RequestBody PartRequestDto dto);

}
