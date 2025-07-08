package pe.upc.cleanview.backend.iam.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.upc.cleanview.backend.iam.domain.model.valueobjects.Roles; // Importar Roles

import org.springframework.security.core.GrantedAuthority; // Importar GrantedAuthority

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority { // Implementa GrantedAuthority

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private Roles name; // El nombre del rol es un enum

  public Role() {}

  public Role(Roles name) {
    this.name = name;
  }

  // Este método es crucial para la conversión de String a Role Entity
  public static Role toRoleFromName(Roles name) { // Recibe un Roles enum
    return new Role(name);
  }

  @Override
  public String getAuthority() {
    return name.name(); // Devuelve el nombre del enum como String (ej. "PERSON", "COMPANY")
  }
}