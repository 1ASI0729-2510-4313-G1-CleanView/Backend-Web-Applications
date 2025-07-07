package pe.upc.cleanview.backend.iam.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pe.upc.cleanview.backend.iam.domain.model.entities.Role;
import pe.upc.cleanview.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.upc.cleanview.backend.tips.domain.model.aggregates.Favorite;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User aggregate root
 * This class represents the aggregate root for the User entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AuditableAbstractAggregateRoot<User>implements UserDetails {

  @NotBlank
  @Size(max = 50)
  @Column(unique = true)
  private String username;

  @NotBlank
  @Size(max = 100)
  @Column(unique = true)
  private String email; // Campo de email añadido

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(   name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  @OneToMany(mappedBy = "user")
  private Set<Favorite> favorites = new HashSet<>();

  public User() {
    this.roles = new HashSet<>();
  }

  public User(String username, String email, String password) { // Constructor con email
    this.username = username;
    this.email = email;
    this.password = password;
    this.roles = new HashSet<>();
  }

  public User(String username, String email, String password, List<Role> roles) { // Constructor con email y roles
    this(username, email, password);
    addRoles(roles);
  }

  /**
   * Add a role to the user
   * @param role the role to add
   * @return the user with the added role
   */
  public User addRole(Role role) {
    this.roles.add(role);
    return this;
  }

  /**
   * Add a list of roles to the user
   * @param roles the list of roles to add
   * @return the user with the added roles
   */
  public User addRoles(List<Role> roles) {
    // *** ¡CAMBIO CLAVE: ELIMINAR LA LLAMADA A validateRoleSet! ***
    // var validatedRoleSet = Role.validateRoleSet(roles); // <-- ELIMINAR ESTA LÍNEA
    this.roles.addAll(roles); // <-- Simplemente añadir todos los roles directamente
    return this;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}