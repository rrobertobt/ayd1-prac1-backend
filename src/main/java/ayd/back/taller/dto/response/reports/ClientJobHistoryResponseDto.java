package ayd.back.taller.dto.response.reports;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Schema(description = "Respuesta de historial de trabajos por cliente")
public class ClientJobHistoryResponseDto {
        @Schema(description = "ID del trabajo", example = "1001")
        Integer jobId;

        @Schema(description = "Placa del vehículo", example = "P123ABC")
        String plate;

        @Schema(description = "Descripción del trabajo", example = "Cambio de aceite y revisión general")
        String description;

        @Schema(description = "Estado del trabajo", example = "FINALIZADO")
        String status;

        @Schema(description = "Fecha de creación del trabajo")
        LocalDateTime createdAt;

        @Schema(description = "Fecha de autorización del trabajo")
        LocalDateTime authorizedAt;
}

