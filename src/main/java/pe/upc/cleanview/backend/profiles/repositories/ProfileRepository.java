package pe.upc.cleanview.backend.profiles.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pe.upc.cleanview.backend.profiles.domain.model.aggregates.Profile;
import pe.upc.cleanview.backend.profiles.domain.model.valueobjects.UserId;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId(UserId userId);
}
