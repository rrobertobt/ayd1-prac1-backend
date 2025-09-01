package ayd.back.taller.dto.response.reports;

import ayd.back.taller.repository.enums.JobStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobReportResponseDto {
    private Integer jobId;
    private Integer vehicleId;
    private String plate;
    private String vehicleModel;
    private JobStatusEnum status;
    private String description;
    private LocalDateTime authorizedAt;
    private LocalDateTime createdAt;
    private List<Integer> assignedUserIds; // ids de empleados/especialistas asignados
    private List<String> taskServiceNames;  // nombres de los tipos de servicios de las tareas
    private Boolean containsSpecialty;      // true si contiene tareas de la specialtyId filtrada
}
