package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

    @Query("SELECT COALESCE(SUM(p.amount),0) FROM PaymentEntity p WHERE p.invoice.id = :invoiceId")
    Double sumPaymentsByInvoice(Integer invoiceId);

}
