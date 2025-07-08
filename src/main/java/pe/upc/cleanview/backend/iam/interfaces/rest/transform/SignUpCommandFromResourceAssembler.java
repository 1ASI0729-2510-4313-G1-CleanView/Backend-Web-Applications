package pe.upc.cleanview.backend.iam.interfaces.rest.transform;

import pe.upc.cleanview.backend.iam.domain.model.commands.SignUpCommand;
import pe.upc.cleanview.backend.iam.domain.model.entities.Role;
import pe.upc.cleanview.backend.iam.domain.model.valueobjects.Roles; // <-- ¡IMPORTANTE: Asegúrate de importar Roles!
import pe.upc.cleanview.backend.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;
import java.util.List; // <-- Asegúrate de importar List

public class SignUpCommandFromResourceAssembler {

  public static SignUpCommand toCommandFromResource(SignUpResource resource) {
    var roles = resource.roles() != null
            ? resource.roles().stream()
            .map(nameString -> Role.toRoleFromName(Roles.valueOf(nameString)))
            .toList()
            : new ArrayList<Role>();
    return new SignUpCommand(resource.username(), resource.email(), resource.password(), roles);
  }
}