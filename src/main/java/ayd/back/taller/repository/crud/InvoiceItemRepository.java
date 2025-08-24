package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.InvoiceItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItemEntity, Integer> {
}
