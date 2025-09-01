package ayd.back.taller.service.reports;

import ayd.back.taller.dto.request.reports.JobReportRequestDto;
import ayd.back.taller.dto.response.reports.JobReportResponseDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.entities.JobEntity;
import ayd.back.taller.repository.enums.UserRoleEnum;
import ayd.back.taller.service.SessionService;
import ayd.back.taller.mapper.JobReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JobReportsService {

    @PersistenceContext
    private final EntityManager em;

    private final SessionService sessionService;
    private final JobReportMapper mapper;

    @Transactional(readOnly = true)
    public List<JobReportResponseDto> getJobsReport(String token, JobReportRequestDto filters) {
        var session = sessionService.validateSessionToken(token);
        if (!session.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User not authorized to generate job reports");
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<JobEntity> cq = cb.createQuery(JobEntity.class);
        Root<JobEntity> root = cq.from(JobEntity.class);
        root.fetch("vehicle", JoinType.LEFT); // traer vehicle
        List<Predicate> predicates = new ArrayList<>();

        var startDate = filters.getStartDate();
        var endDate = filters.getEndDate();
        var status = filters.getStatus();
        var plate = filters.getPlate();
        var vehicleId = filters.getVehicleId();
        var descriptionQuery = filters.getQ();
        var userId = filters.getUserId();
        var specialtyId = filters.getSpecialtyId();

        if (startDate != null) {
            LocalDateTime startDt = startDate.atStartOfDay();
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), startDt));
        }
        if (endDate != null) {
            LocalDateTime endDt = endDate.atTime(23,59,59);
            predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), endDt));
        }

        // placa (join vehicle)
        if (plate != null && !plate.isBlank()) {
            Join<Object, Object> vehicleJoin = root.join("vehicle", JoinType.INNER);
            predicates.add(cb.like(cb.lower(vehicleJoin.get("plate")), "%" + plate.toLowerCase() + "%"));
        }

        // estado del trabajo (status) -> como texto
        if (status != null) {
            predicates.add(cb.equal(root.get("status"), status.name()));
        }

        if (vehicleId != null) {
            predicates.add(cb.equal(root.get("vehicle").get("id"), vehicleId));
        }

        if (descriptionQuery != null && !descriptionQuery.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("description")), "%" + descriptionQuery.toLowerCase() + "%"));
        }

        // userId (empleado/especialista involucrado) -> join job_assignments
        if (userId != null) {
            // join job_assignments table via relationship name (assume field name "assignments" or "jobAssignments")
            // adjust the join attribute name if your entity uses a different property name
            Join<Object, Object> assignJoin = root.join("assignments", JoinType.INNER);
            predicates.add(cb.equal(assignJoin.get("user").get("id"), userId));
        }

        if (specialtyId != null) {
            Join<Object, Object> taskJoin = root.join("tasks", JoinType.INNER);
            Join<Object, Object> serviceJoin = taskJoin.join("service", JoinType.INNER);
            predicates.add(cb.equal(serviceJoin.get("specialty").get("id"), specialtyId));
        }

        cq.select(root).distinct(true);
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        cq.orderBy(cb.desc(root.get("createdAt")));

        TypedQuery<JobEntity> query = em.createQuery(cq);

        List<JobEntity> jobs = query.getResultList();

        List<JobReportResponseDto> data = jobs.stream()
                .map(job -> mapper.toDto(job, specialtyId))
                .toList();

        return data;
    }
}
