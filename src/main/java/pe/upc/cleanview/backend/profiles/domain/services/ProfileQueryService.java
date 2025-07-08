package pe.upc.cleanview.backend.profiles.domain.services;


import pe.upc.cleanview.backend.profiles.domain.model.aggregates.Profile;
import pe.upc.cleanview.backend.profiles.domain.model.queries.GetAllProfilesQuery;
import pe.upc.cleanview.backend.profiles.domain.model.queries.GetProfileAByUserIdQuery;
import pe.upc.cleanview.backend.profiles.domain.model.queries.GetProfileByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByIdQuery query);


    List<Profile> handle(GetAllProfilesQuery query);
    Optional<Profile> handle(GetProfileAByUserIdQuery query);

}
