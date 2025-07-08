package pe.upc.cleanview.backend.iam.domain.services;

import pe.upc.cleanview.backend.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
  void handle(SeedRolesCommand command);
}
