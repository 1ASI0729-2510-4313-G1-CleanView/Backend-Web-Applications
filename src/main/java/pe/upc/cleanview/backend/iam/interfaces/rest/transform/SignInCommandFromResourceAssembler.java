package pe.upc.cleanview.backend.iam.interfaces.rest.transform;

import pe.upc.cleanview.backend.iam.domain.model.commands.SignInCommand;
import pe.upc.cleanview.backend.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
  public static SignInCommand toCommandFromResource(SignInResource resource) {
    return new SignInCommand(resource.email(), resource.password());
  }
}