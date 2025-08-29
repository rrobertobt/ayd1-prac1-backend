package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.PartCatalogRequestDto;
import ayd.back.taller.dto.response.PartCatalogResponseDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/catalog")
public interface CatalogApi {

    @Operation(
            summary = "Obtener inventario del taller",
            description = "Devuelve un listado de todos los repuestos del taller con stock",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Inventario encontrado correctamente")
            }
    )
    @GetMapping()
    ResponseEntity<List<PartCatalogResponseDto>> findCatalog(@RequestHeader(value = "session-token") String token);


    @Operation(
            summary = "Obtener inventario de un proveedor específico",
            description = "Devuelve un listado de todos los repuestos del proveedor con stock",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Inventario encontrado correctamente")
            }
    )
    @GetMapping("/supplier/{supplierId}")
    ResponseEntity<List<PartCatalogResponseDto>> findCatalog(@PathVariable Integer supplierId, @RequestHeader(value = "session-token") String token);


    @Operation(
            summary = "Obtener un item de inventario",
            description = "Obtiene un item de un catalogo según su id. Para obtener stock o precio de un item",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item encontrado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Item no encontrado")
            }
    )
    @GetMapping("/{catalogId}")
    ResponseEntity<PartCatalogResponseDto> getCatalogItem(@PathVariable Integer catalogId, @RequestHeader(value = "session-token") String token);


    @Operation(
            summary = "Inserta o actualiza un item de inventario",
            description = "Si el item en la solicitud trae id y existe en la base de datos lo actualiza, si no trae id lo inserta",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item actualizado correctamente"),
                    @ApiResponse(responseCode = "201", description = "Item creado correctamente"),
                    @ApiResponse(responseCode = "403", description = "El usuario no es admin o proveedor"),
                    @ApiResponse(responseCode = "400", description = "Solicitud sin partId"),
                    @ApiResponse(responseCode = "404", description = "Repuesto no encontrado (partId)"),
                    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado (supplierId)"),
                    @ApiResponse(responseCode = "404", description = "Item no encontrado (catalogId)")
            }
    )
    @PostMapping()
    ResponseEntity<ResponseSuccessDto> upsertCatalogEntry(@RequestBody PartCatalogRequestDto dto, @RequestHeader(value = "session-token") String token);


    @Operation(
            summary = "Actualiza el precio de un item",
            description = "Toma el precio enviado en el cuerpo y lo usa para reemplazar el precio anterior",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Precio actualizado correctamente"),
                    @ApiResponse(responseCode = "403", description = "El item no le pertenece al usuario"),
                    @ApiResponse(responseCode = "404", description = "Item no encontrado"),
                    @ApiResponse(responseCode = "400", description = "Nuevo precio no puede ser negativo")
            }
    )
    @PutMapping("/{id}/price")
    ResponseEntity<ResponseSuccessDto> updatePrice(@PathVariable Integer id, @RequestBody Map<String, Double> body, @RequestHeader(value = "session-token") String token);


    @Operation(
            summary = "Actualiza el stock de un item",
            description = "Toma el stock enviado en el cuerpo y lo usa para reemplazar el anterior",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente"),
                    @ApiResponse(responseCode = "403", description = "El item no le pertenece al usuario"),
                    @ApiResponse(responseCode = "404", description = "Item no encontrado"),
                    @ApiResponse(responseCode = "400", description = "Nuevo stock no puede ser negativo")
            }
    )
    @PutMapping("/{id}/stock")
    ResponseEntity<ResponseSuccessDto> updateStock(@PathVariable Integer id, @RequestBody Map<String, Integer> body, @RequestHeader(value = "session-token") String token);

}
