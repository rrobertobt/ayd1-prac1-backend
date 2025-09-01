package ayd.back.taller.dto.request.jobs;

import ayd.back.taller.repository.enums.TaskStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskStatusRequestDto {
    @NotNull(message = "El nuevo estado de la tarea es obligatorio")
    private TaskStatusEnum newStatus;
}
