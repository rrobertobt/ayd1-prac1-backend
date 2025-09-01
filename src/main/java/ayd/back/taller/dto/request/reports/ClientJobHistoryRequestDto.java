package ayd.back.taller.dto.request.reports;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;

import java.time.LocalDate;

@Setter
@Getter
@ParameterObject
@Schema(description = "Request para obtener historial de trabajos de un cliente")
public class ClientJobHistoryRequestDto {
        @Schema(description = "ID del cliente", example = "10")
        private Integer clientId;

        @Schema(description = "Correo del cliente", example = "cliente@email.com")
        private String email;

        @Schema(description = "Nombre del cliente", example = "Juan Pérez")
        private String name;

        @Schema(description = "NIT del cliente", example = "12345678")
        private Integer nit;

}
