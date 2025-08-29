package ayd.back.taller.repository.crud;

import ayd.back.taller.repository.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Integer> {



    @Query(value = "SELECT * FROM vehicles WHERE vin = ? OR plate = ? ;", nativeQuery = true)
    Optional<VehicleEntity> findByVinOrPlate(String vin, String plate);


    @Query(value = "SELECT * FROM vehicle WHERE owner_id = ? and plate = ?;", nativeQuery = true)
    Optional<VehicleEntity> findByOwner(Integer ownerId, String plate);

}
