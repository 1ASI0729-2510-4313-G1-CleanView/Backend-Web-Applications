package pe.upc.cleanview.backend.iam.domain.model.queries;

import pe.upc.cleanview.backend.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}
