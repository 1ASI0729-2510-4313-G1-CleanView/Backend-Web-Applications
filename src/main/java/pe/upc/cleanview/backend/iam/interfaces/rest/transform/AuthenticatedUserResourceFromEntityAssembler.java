package pe.upc.cleanview.backend.iam.interfaces.rest.transform;

import pe.upc.cleanview.backend.iam.domain.model.aggregates.User;
import pe.upc.cleanview.backend.iam.interfaces.rest.resources.AuthenticatedUserResource;
import java.util.List;
import java.util.stream.Collectors;

public class AuthenticatedUserResourceFromEntityAssembler {

  public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {

    List<String> roles = user.getRoles().stream()
            .map(role -> role.getName().name())
            .collect(Collectors.toList());


    return new AuthenticatedUserResource(
            user.getId(),
            user.getUsername(),
            token,
            roles
    );
  }
}
