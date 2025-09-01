package ayd.back.taller.controller.api;

import ayd.back.taller.dto.response.JobDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/jobs")
public interface JobApi {

        @Operation(
                summary = "Obtener trabajos relacionados al usuario",
                description = "Devuelve los trabajos relacionados al usuario autenticado según su rol:\n" +
                        "- ADMIN: todos los trabajos.\n" +
                        "- EMPLEADO/ESPECIALISTA: trabajos en los que está asignado.\n" +
                        "- CLIENTE: trabajos de los vehículos que posee."
        )
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Lista de trabajos obtenida exitosamente"),
                @ApiResponse(responseCode = "400", description = "Token inválido o malformado"),
                @ApiResponse(responseCode = "401", description = "No autorizado, sesión no válida"),
                @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @GetMapping("/my")
        ResponseEntity<List<JobDto>> getJobsForUser(
                @RequestHeader("session-token") String token);

}
