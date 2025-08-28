package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, String> {


    @Query(value = "SELECT * FROM session WHERE user_id = ? ;", nativeQuery = true)
    Optional<SessionEntity> findByUser(Integer user);


    @Query(value = "UPDATE session SET token = ? WHERE token = ? ;", nativeQuery = true)
    void updateToken(String actualToken, String newToken);

}
