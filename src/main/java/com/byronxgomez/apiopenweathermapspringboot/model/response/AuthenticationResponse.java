package com.byronxgomez.apiopenweathermapspringboot.model.response;

import com.byronxgomez.apiopenweathermapspringboot.model.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la respuesta de autenticación.
 * Contiene el token de autenticación, el nombre de usuario,
 * el correo electrónico y el rol del usuario autenticado.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private String token;
  private String username;
  private String email;
  private Rol role;
}
