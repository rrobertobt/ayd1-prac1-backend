package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.PartRequestDto;
import ayd.back.taller.dto.response.PartResponseDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/part")
public interface PartApi {

    @Operation(
            summary = "Obtener diccionario de repuestos",
            description = "Devuelve un listado de todos los tipos de repuestos que se manejan en el sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Repuestos encontrados correctamente")
            }
    )
    @GetMapping
    ResponseEntity<List<PartResponseDto>> findAllParts(@RequestHeader(value = "session-token") String token);


    @Operation(
            summary = "Obtener un tipo de repuesto específico",
            description = "Devuelve Los datos de un repuesto específico según su id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Repuesto encontrado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Repuesto no encontrado")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<PartResponseDto> findPartById(@PathVariable Integer id, @RequestHeader(value = "session-token") String token);


    @Operation(
            summary = "Crear tipo de repuesto",
            description = "Registra un nuevo tipo de repuesto en el sistema",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Repuesto creado correctamente"),
                    @ApiResponse(responseCode = "403", description = "El usuario no es admin"),
                    @ApiResponse(responseCode = "400", description = "Faltan campos requeridos")
            }
    )
    @PostMapping
    ResponseEntity<ResponseSuccessDto> createPart(@RequestBody PartRequestDto dto, @RequestHeader(value = "session-token") String token);

    @Operation(
            summary = "Modificar tipo de repuesto",
            description = "Actualiza los datos de un tipo de repuesto según su id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Repuesto actualizado correctamente"),
                    @ApiResponse(responseCode = "403", description = "El usuario no es admin"),
                    @ApiResponse(responseCode = "400", description = "Faltan campos requeridos")
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<ResponseSuccessDto> updatePart(@PathVariable Integer id, @RequestBody PartRequestDto dto, @RequestHeader(value = "session-token") String token);

}
