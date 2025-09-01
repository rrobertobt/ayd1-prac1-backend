package ayd.back.taller.repository.crud;


import ayd.back.taller.repository.entities.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer> {
    
    List<InvoiceEntity> findByClientId(Integer clientId);

    List<InvoiceEntity> findByIssuedAtBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
