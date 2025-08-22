package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCrud extends JpaRepository<UserEntity, Integer> {

    @Query(value = "SELECT * FROM users WHERE email = ?;", nativeQuery = true)
    Optional<UserEntity> getUserByEmail(String email);

}
