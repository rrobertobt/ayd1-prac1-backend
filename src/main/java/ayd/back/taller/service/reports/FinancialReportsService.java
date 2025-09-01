package ayd.back.taller.service.reports;


import ayd.back.taller.dto.request.reports.IncomeExpenseReportRequestDto;
import ayd.back.taller.dto.response.reports.IncomeExpenseReportResponseDto;
import ayd.back.taller.dto.response.reports.PaymentItemDto;
import ayd.back.taller.dto.response.reports.PurchaseOrderItemDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.entities.PaymentEntity;
import ayd.back.taller.repository.entities.PurchaseOrderEntity;
import ayd.back.taller.repository.enums.FinancialReportType;
import ayd.back.taller.repository.enums.PaymentMethodEnum;
import ayd.back.taller.repository.enums.PurchaseStatusEnum;
import ayd.back.taller.repository.enums.UserRoleEnum;
import ayd.back.taller.service.SessionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinancialReportsService {

    @PersistenceContext
    private final EntityManager em;

    private final SessionService sessionService;

    @Transactional(readOnly = true)
    public IncomeExpenseReportResponseDto generateCashflowReport(String token, IncomeExpenseReportRequestDto request) {
        sessionService.isAdmin(token);

        // validar fechas
        LocalDate start = request.getStartDate();
        LocalDate end = request.getEndDate();
        if (start != null && end != null && start.isAfter(end)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "startDate no puede ser posterior a endDate");
        }

        // construir y ejecutar queries según tipo solicitado
        FinancialReportType type = Optional.ofNullable(request.getType()).orElse(FinancialReportType.BOTH);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;
        List<PaymentItemDto> payments = Collections.emptyList();
        List<PurchaseOrderItemDto> purchases = Collections.emptyList();

        if (type == FinancialReportType.INCOME || type == FinancialReportType.BOTH) {
            StringBuilder paySql = new StringBuilder("SELECT p.id, p.invoice.id, p.amount, p.method, p.paidAt FROM PaymentEntity p WHERE 1=1");
            if (start != null) paySql.append(" AND p.paidAt >= :start");
            if (end != null) paySql.append(" AND p.paidAt <= :end");
            Query qPay = em.createQuery(paySql.toString());
            if (start != null) qPay.setParameter("start", start.atStartOfDay());
            if (end != null) qPay.setParameter("end", end.atTime(23, 59, 59));

            @SuppressWarnings("unchecked")
            List<Object[]> payRows = (List<Object[]>) qPay.getResultList();

            payments = payRows.stream().map(row -> {
                Integer id = (Integer) row[0];
                Integer invoiceId = row[1] == null ? null : (Integer) row[1];
                BigDecimal amount = BigDecimal.valueOf((Double) row[2]);
                PaymentMethodEnum method = (PaymentMethodEnum) row[3];
                LocalDateTime paidAt = (LocalDateTime) row[4];
                return new PaymentItemDto(id, invoiceId, amount, method.name(), paidAt);
            }).collect(Collectors.toList());

            totalIncome = payments.stream()
                    .map(PaymentItemDto::getAmount)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        if (type == FinancialReportType.EXPENSE || type == FinancialReportType.BOTH) {
            StringBuilder poSql = new StringBuilder("SELECT po.id, po.supplier.id, po.status, po.total, po.orderDate, po.createdAt FROM PurchaseOrderEntity po WHERE 1=1");
            if (start != null) poSql.append(" AND po.createdAt >= :start");
            if (end != null) poSql.append(" AND po.createdAt <= :end");
            Query qPo = em.createQuery(poSql.toString());
            if (start != null) qPo.setParameter("start", start.atStartOfDay());
            if (end != null) qPo.setParameter("end", end.atTime(23, 59, 59));

            @SuppressWarnings("unchecked")
            List<Object[]> poRows = (List<Object[]>) qPo.getResultList();

            purchases = poRows.stream().map(row -> {
                Integer id = (Integer) row[0];
                Integer supplierId = row[1] == null ? null : (Integer) row[1];
                PurchaseStatusEnum status = (PurchaseStatusEnum) row[2];
                BigDecimal total = BigDecimal.valueOf((Double) row[3]);
                LocalDateTime orderDate = row[4] == null ? null : ((LocalDateTime) row[4]);
                LocalDateTime createdAt = row[5] == null ? null : ((LocalDateTime) row[5]);
                return new PurchaseOrderItemDto(id, supplierId, status.name(), total, orderDate.toLocalDate(), createdAt);
            }).collect(Collectors.toList());

            totalExpenses = purchases.stream()
                    .map(PurchaseOrderItemDto::getTotal)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        BigDecimal net = totalIncome.subtract(totalExpenses);

        return new IncomeExpenseReportResponseDto(
                totalIncome,
                totalExpenses,
                net,
                payments,
                purchases
        );
    }
}
