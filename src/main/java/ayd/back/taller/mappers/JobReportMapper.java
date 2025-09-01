package ayd.back.taller.mappers;

import ayd.back.taller.dto.response.reports.JobReportResponseDto;
import ayd.back.taller.repository.entities.JobEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobReportMapper {

    private final EntityManager em;

    public JobReportResponseDto toDto(JobEntity job, Integer specialtyId) {
        JobReportResponseDto dto = new JobReportResponseDto();
        dto.setJobId(job.getId());

        if (job.getVehicle() != null) {
            dto.setVehicleId(job.getVehicle().getId());
            dto.setPlate(job.getVehicle().getPlate());
            dto.setVehicleModel(job.getVehicle().getModel());
        }

        dto.setStatus(job.getStatus());
        dto.setDescription(job.getDescription());
        dto.setAuthorizedAt(job.getAuthorizedAt());
        dto.setCreatedAt(job.getCreatedAt());

        // empleados asignados
        List<Integer> assigned = em.createQuery(
                        "select ja.user.id from JobAssignmentsEntity ja where ja.job.id = :jobId", Integer.class)
                .setParameter("jobId", job.getId()).getResultList();
        dto.setAssignedUserIds(assigned);

        // nombres de servicios de tareas
        List<String> services = em.createQuery(
                        "select s.name from JobTaskEntity t join t.service s where t.job.id = :jobId", String.class)
                .setParameter("jobId", job.getId()).getResultList();
        dto.setTaskServiceNames(services);

        // contiene specialty?
        if (specialtyId != null) {
            Boolean contains = em.createQuery(
                            "select count(t) from JobTaskEntity t join t.service s where t.job.id = :jobId and s.specialty.id = :specId",
                            Long.class)
                    .setParameter("jobId", job.getId())
                    .setParameter("specId", specialtyId)
                    .getSingleResult() > 0;
            dto.setContainsSpecialty(contains);
        } else {
            dto.setContainsSpecialty(null);
        }

        return dto;
    }
}
