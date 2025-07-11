package pe.upc.cleanview.backend.iam.infrastructure.authorization.sfs.services;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import pe.upc.cleanview.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;

/**
 * This class is responsible for providing the user details to the Spring Security framework.
 * It implements the UserDetailsService interface.
 */
@Primary
@Service(value = "defaultUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * This method is responsible for loading the user details from the database.
   * It will now search by email instead of username.
   * @param email The email. (Still named 'username' due to UserDetailsService contract)
   * @return The UserDetails object.
   * @throws UsernameNotFoundException If the user is not found.
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    var user = userRepository.findByEmail(email)
            .orElseThrow(
                    () -> new UsernameNotFoundException("User not found with email: " + email));
    return UserDetailsImpl.build(user);
  }
}