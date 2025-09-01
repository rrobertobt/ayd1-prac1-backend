package ayd.back.taller.service.reports;

import ayd.back.taller.dto.request.reports.JobReportRequestDto;
import ayd.back.taller.dto.response.reports.JobReportResponseDto;
import ayd.back.taller.repository.entities.JobEntity;
import ayd.back.taller.service.SessionService;
import ayd.back.taller.mappers.JobReportMapper;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
        sessionService.isAdmin(token);

        StringBuilder sql = new StringBuilder();
        sql.append("""
            SELECT DISTINCT j.*
            FROM jobs j
            LEFT JOIN vehicles v ON j.vehicle_id = v.id
            LEFT JOIN job_assignments ja ON ja.job_id = j.id
            LEFT JOIN job_tasks jt ON jt.job_id = j.id
            LEFT JOIN service_types st ON jt.service_id = st.id
            LEFT JOIN specialties sp ON st.specialty_id = sp.id
            WHERE 1=1
        """);

        Map<String, Object> params = new HashMap<>();

        if (filters.getStartDate() != null) {
            sql.append(" AND j.created_at >= :startDate ");
            params.put("startDate", filters.getStartDate().atStartOfDay());
        }
        if (filters.getEndDate() != null) {
            sql.append(" AND j.created_at <= :endDate ");
            params.put("endDate", filters.getEndDate().atTime(23,59,59));
        }
        if (filters.getPlate() != null && !filters.getPlate().isBlank()) {
            sql.append(" AND LOWER(v.plate) LIKE :plate ");
            params.put("plate", "%" + filters.getPlate().toLowerCase() + "%");
        }
        if (filters.getStatus() != null) {
            sql.append(" AND j.status = :status ");
            params.put("status", filters.getStatus().name());
        }
        if (filters.getVehicleId() != null) {
            sql.append(" AND v.id = :vehicleId ");
            params.put("vehicleId", filters.getVehicleId());
        }
        if (filters.getQ() != null && !filters.getQ().isBlank()) {
            sql.append(" AND LOWER(j.description) LIKE :desc ");
            params.put("desc", "%" + filters.getQ().toLowerCase() + "%");
        }
        if (filters.getUserId() != null) {
            sql.append(" AND ja.user_id = :userId ");
            params.put("userId", filters.getUserId());
        }
        if (filters.getSpecialtyId() != null) {
            sql.append(" AND sp.id = :specialtyId ");
            params.put("specialtyId", filters.getSpecialtyId());
        }

        sql.append(" ORDER BY j.created_at DESC ");

        Query nativeQuery = em.createNativeQuery(sql.toString(), JobEntity.class);

        params.forEach(nativeQuery::setParameter);

        @SuppressWarnings("unchecked")
        List<JobEntity> jobs = nativeQuery.getResultList();


        List<JobReportResponseDto> data = jobs.stream()
                .map(job -> mapper.toDto(job, filters.getSpecialtyId()))
                .toList();

        return data;
    }
}
