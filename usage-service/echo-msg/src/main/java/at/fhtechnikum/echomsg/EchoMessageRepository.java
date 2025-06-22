package at.fhtechnikum.echomsg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EchoMessageRepository extends JpaRepository<EchoMessage, Long> {
    List<EchoMessage> findAll();
    Optional<EchoMessage> findById(Long id);
}