package pe.upc.cleanview.backend.iam.interfaces.acl;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.iam.domain.model.aggregates.User;
import pe.upc.cleanview.backend.iam.domain.model.commands.SignUpCommand;
import pe.upc.cleanview.backend.iam.domain.model.entities.Role;
import pe.upc.cleanview.backend.iam.domain.model.queries.GetUserByIdQuery;
import pe.upc.cleanview.backend.iam.domain.model.queries.GetUserByUsernameQuery;
import pe.upc.cleanview.backend.iam.domain.services.UserCommandService;
import pe.upc.cleanview.backend.iam.domain.services.UserQueryService;
import pe.upc.cleanview.backend.iam.domain.model.valueobjects.Roles;

import java.util.ArrayList;
import java.util.List;

/**
 * IamContextFacade
 * <p>
 * This class is a facade for the IAM context. It provides a simple interface for other
 * bounded contexts to interact with the
 * IAM context.
 * This class is a part of the ACL layer.
 * </p>
 *
 */
@Service
public class IamContextFacade {

  private final UserCommandService userCommandService;
  private final UserQueryService userQueryService;

  public IamContextFacade(UserCommandService userCommandService,
                          UserQueryService userQueryService) {
    this.userCommandService = userCommandService;
    this.userQueryService = userQueryService;
  }

  /**
   * Creates a user with the given username and password.
   * @param username The username of the user.
   * @param password The password of the user.
   * @return The id of the created user.
   */
  public Long createUser(String username, String password) {
    var defaultRole = Role.toRoleFromName(Roles.PERSON);
    var signUpCommand = new SignUpCommand(username, null, password, List.of(defaultRole));
    var result = userCommandService.handle(signUpCommand);
    if (result.isEmpty()) return 0L;
    return result.get().getId();
  }

  /**
   * Creates a user with the given username, password and roles.
   * @param username The username of the user.
   * @param password The password of the user.
   * @param roleNames The names of the roles of the user. When a role does not exist,
   * it is ignored.
   * @return The id of the created user.
   */
  public Long createUser(String username, String password, List<String> roleNames) {
    var roles = roleNames != null
            ? roleNames.stream()
            .map(nameString -> Role.toRoleFromName(Roles.valueOf(nameString)))
            .toList()
            : new ArrayList<Role>();
    var signUpCommand = new SignUpCommand(username, null, password, roles);
    var result = userCommandService.handle(signUpCommand);
    if (result.isEmpty())
      return 0L;
    return result.get().getId();
  }

  /**
   * Fetches the id of the user with the given username.
   * @param username The username of the user.
   * @return The id of the user.
   */
  public Long fetchUserIdByUsername(String username) {
    var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
    var result = userQueryService.handle(getUserByUsernameQuery);
    if (result.isEmpty())
      return 0L;
    return result.get().getId();
  }

  /**
   * Fetches the username of the user with the given id.
   * @param userId The id of the user.
   * @return The username of the user.
   */
  public String fetchUsernameByUserId(Long userId) {
    var getUserByIdQuery = new GetUserByIdQuery(userId);
    var result = userQueryService.handle(getUserByIdQuery);
    if (result.isEmpty())
      return Strings.EMPTY;
    return result.get().getUsername();
  }
  /**
   * hecho para lo que pide John
   * Fetches the object User of the user with the given id.
   * @param userId The id of the user.
   * @return The object User.
   */
  public User fetchUserById(Long userId) {
    var getUserByIdQuery = new GetUserByIdQuery(userId);
    var result = userQueryService.handle(getUserByIdQuery);
    return result.orElse(null);
  }

  /**
   * Retrieves the ID of the currently authenticated user from the Spring Security context.
   * @return The ID of the authenticated user, or null if no user is authenticated or
   * the principal is not a UserDetails object.
   */
  public Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
      return null;
    }

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    return fetchUserIdByUsername(userDetails.getUsername());
  }
}
