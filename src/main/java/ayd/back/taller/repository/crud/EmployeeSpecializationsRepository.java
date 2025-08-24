package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.EmployeeSpecializationId;
import ayd.back.taller.repository.entities.EmployeeSpecializations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSpecializationsRepository extends JpaRepository<EmployeeSpecializations, EmployeeSpecializationId> {


}
