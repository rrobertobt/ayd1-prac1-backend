package ayd.back.taller.dto.response.jobs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LogTypeResponseDto {
    private String name;        // Nombre del tipo de log (enum.name())
    private String description; // Descripción legible para el frontend
}
