package ayd.back.taller.service.reports;

import ayd.back.taller.dto.request.reports.PartsUsageReportRequestDto;
import ayd.back.taller.dto.response.reports.PartsUsageReportResponseDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.service.SessionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartsUsageReportService {

    private final SessionService sessionService;

    @PersistenceContext
    private EntityManager em;

    public List<PartsUsageReportResponseDto> getPartsUsageReport(String token, PartsUsageReportRequestDto filters) {
        sessionService.isAdmin(token);

        if (filters.getStartDate() != null && filters.getEndDate() != null &&
                filters.getStartDate().isAfter(filters.getEndDate())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        if (filters.getMinPrice() != null && filters.getMaxPrice() != null &&
                filters.getMinPrice().compareTo(filters.getMaxPrice()) > 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "El precio mínimo no puede ser mayor al precio máximo");
        }

        // Construcción dinámica de query
        StringBuilder sb = new StringBuilder("""
            SELECT  p.id::int,
                    p.name,
                    SUM(jp.amount)::int,
                    AVG(jp.unit_price)::double precision,
                    SUM(jp.unit_price * jp.amount)::double precision
            FROM job_parts jp
            JOIN parts p ON jp.part_id = p.id
            JOIN jobs j ON jp.job_id = j.id
            JOIN vehicles v ON j.vehicle_id = v.id
            WHERE 1=1
        """);

        if (filters.getStartDate() != null) sb.append(" AND jp.created_at >= :startDate");
        if (filters.getEndDate() != null) sb.append(" AND jp.created_at <= :endDate");
        if (filters.getPartTypeId() != null) sb.append(" AND p.id = :partTypeId");
        if (StringUtils.hasText(filters.getBrand())) sb.append(" AND v.brand = :brand");
        if (StringUtils.hasText(filters.getModel())) sb.append(" AND v.model = :model");
        if (filters.getYear() != null) sb.append(" AND v.year = :year");
        if (filters.getMinPrice() != null) sb.append(" AND jp.unit_price >= :minPrice");
        if (filters.getMaxPrice() != null) sb.append(" AND jp.unit_price <= :maxPrice");

        sb.append(" GROUP BY p.id, p.name");

        var query = em.createNativeQuery(sb.toString(), PartsUsageReportResponseDto.class);

        if (filters.getStartDate() != null) query.setParameter("startDate", filters.getStartDate().atStartOfDay());
        if (filters.getEndDate() != null) query.setParameter("endDate", filters.getEndDate().atTime(23, 59, 59));
        if (filters.getPartTypeId() != null) query.setParameter("partTypeId", filters.getPartTypeId());
        if (StringUtils.hasText(filters.getBrand())) query.setParameter("brand", filters.getBrand());
        if (StringUtils.hasText(filters.getModel())) query.setParameter("model", filters.getModel());
        if (filters.getYear() != null) query.setParameter("year", filters.getYear());
        if (filters.getMinPrice() != null) query.setParameter("minPrice", filters.getMinPrice());
        if (filters.getMaxPrice() != null) query.setParameter("maxPrice", filters.getMaxPrice());

        return query.getResultList();
    }
}
