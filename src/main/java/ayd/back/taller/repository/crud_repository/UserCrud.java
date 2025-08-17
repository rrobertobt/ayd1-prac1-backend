package ayd.back.taller.repository.crud_repository;

import ayd.back.taller.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCrud extends JpaRepository<UserEntity, Integer> {
}
