package pe.upc.cleanview.backend.iam.domain.services;

import org.apache.commons.lang3.tuple.ImmutablePair;
import pe.upc.cleanview.backend.iam.domain.model.aggregates.User;
import pe.upc.cleanview.backend.iam.domain.model.commands.SignInCommand;
import pe.upc.cleanview.backend.iam.domain.model.commands.SignUpCommand;

import java.util.Optional;

public interface UserCommandService {
  Optional<ImmutablePair<User, String>> handle(SignInCommand command);
  Optional<User> handle(SignUpCommand command);
}
