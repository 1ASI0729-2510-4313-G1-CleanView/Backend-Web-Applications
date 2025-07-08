package pe.upc.cleanview.backend.iam.interfaces.rest.transform;

import pe.upc.cleanview.backend.iam.domain.model.aggregates.User;
import pe.upc.cleanview.backend.iam.domain.model.entities.Role;
import pe.upc.cleanview.backend.iam.interfaces.rest.resources.UserResource;

import java.util.List; // Asegúrate de importar List

public class UserResourceFromEntityAssembler {

  public static UserResource toResourceFromEntity(User user) {
    var roles = user.getRoles().stream()
            .map(role -> role.getName().name())
            .toList();
    return new UserResource(user.getId(), user.getUsername(), roles);
  }
}