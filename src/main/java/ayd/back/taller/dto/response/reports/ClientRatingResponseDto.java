package ayd.back.taller.dto.response.reports;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ClientRatingResponseDto {
            @Schema(description = "ID del log", example = "101")
            Integer logId;

            @Schema(description = "ID del trabajo asociado", example = "1001")
            Integer jobId;

            @Schema(description = "Descripción de la calificación", example = "10 Excelente atención")
            String description;

            @Schema(description = "Fecha y hora en que se registró la calificación")
            LocalDateTime occurredAt;

}
