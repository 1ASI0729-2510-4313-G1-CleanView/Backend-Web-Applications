package pe.upc.cleanview.backend.iam.domain.services;

import pe.upc.cleanview.backend.iam.domain.model.entities.Role;
import pe.upc.cleanview.backend.iam.domain.model.queries.GetAllRolesQuery;
import pe.upc.cleanview.backend.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
  List<Role> handle(GetAllRolesQuery query);
  Optional<Role> handle(GetRoleByNameQuery query);
}
