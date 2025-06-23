package at.fhtechnikum.echoservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UsageRepository extends JpaRepository<Usage, LocalDateTime> {
    Optional<Usage> findByHour(LocalDateTime hour);
}