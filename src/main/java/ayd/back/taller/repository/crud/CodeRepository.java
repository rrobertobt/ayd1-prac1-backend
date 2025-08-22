package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Integer> {


    @Query(value = "SELECT * FROM mfa_codes WHERE user_id = ?",nativeQuery = true)
    Optional<CodeEntity> findByUserId(Integer userId);

    @Query(value = "SELECT * FROM mfa_codes WHERE code = ?",nativeQuery = true)
    Optional<CodeEntity> findByCode(String userId);

}
