package at.fhtechnikum.communityrestapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface EnergyDataRepository extends JpaRepository<EnergyData, LocalDateTime>{
    Optional<EnergyData> findByDate(LocalDateTime date);
    ArrayList<EnergyData> findByDateBetween(LocalDateTime start, LocalDateTime end);
}