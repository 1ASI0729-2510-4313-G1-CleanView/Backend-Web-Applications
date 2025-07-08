package pe.upc.cleanview.backend.iam.interfaces.rest.transform;

import pe.upc.cleanview.backend.iam.domain.model.entities.Role;
import pe.upc.cleanview.backend.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {

  public static RoleResource toResourceFromEntity(Role role) {

    return new RoleResource(role.getId(), role.getName().name());
  }
}