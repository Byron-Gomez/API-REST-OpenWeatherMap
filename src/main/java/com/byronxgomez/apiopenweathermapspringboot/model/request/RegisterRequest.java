package com.byronxgomez.apiopenweathermapspringboot.model.request;

import com.byronxgomez.apiopenweathermapspringboot.model.enums.Rol;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la solicitud de registro de un usuario.
 * Contiene los campos necesarios para el registro, como el nombre, apellido, nombre de usuario, email, contraseña y rol.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotEmpty
  private String firstname;
  @NotEmpty
  private String lastname;
  @NotEmpty
  private String username;
  @NotEmpty
  private String email;
  @NotEmpty
  private String password;

  private Rol rol;
}
