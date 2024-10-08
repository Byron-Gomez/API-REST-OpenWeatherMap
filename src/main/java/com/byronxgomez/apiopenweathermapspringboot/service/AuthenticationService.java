package com.byronxgomez.apiopenweathermapspringboot.service;

import com.byronxgomez.apiopenweathermapspringboot.model.entity.Token;
import com.byronxgomez.apiopenweathermapspringboot.model.entity.Usuario;
import com.byronxgomez.apiopenweathermapspringboot.model.enums.Rol;
import com.byronxgomez.apiopenweathermapspringboot.model.enums.TokenType;
import com.byronxgomez.apiopenweathermapspringboot.model.request.AuthenticationRequest;
import com.byronxgomez.apiopenweathermapspringboot.model.request.RegisterRequest;
import com.byronxgomez.apiopenweathermapspringboot.model.response.AuthenticationResponse;
import com.byronxgomez.apiopenweathermapspringboot.repository.TokenRepository;
import com.byronxgomez.apiopenweathermapspringboot.repository.UsuarioRepository;
import com.byronxgomez.apiopenweathermapspringboot.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UsuarioRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtils jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {


    // Crea un nuevo objeto Usuario con los detalles proporcionados en la solicitud
    if (request.getRol() == null) {
      request.setRol(Rol.USER);
    }
    var user = Usuario.builder()
            .firstname(request.getFirstname().trim())
            .lastname(request.getLastname().trim())
            .email(request.getEmail().trim())
            .username(request.getUsername().trim())
            .password(passwordEncoder.encode(request.getPassword().trim()))
            .role(request.getRol())
            .build();
    // Guarda el usuario en el repositorio
    var savedUser = repository.save(user);
    // Genera el token JWT y el token de refresco
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    // Guarda el token del usuario en el repositorio de tokens
    saveUserToken(savedUser, jwtToken);
    // Crea y devuelve una respuesta de autenticación con el token JWT y otros detalles del usuario
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .email(user.getEmail())
            .username(user.getUsername())
            .role(user.getRole())
            .build();
  }

  // Autenticación de un usuario existente
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    // Autentica al usuario con el administrador de autenticación
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername().trim(),
                    request.getPassword().trim()
            )
    );
    // Busca al usuario por su nombre de usuario en el repositorio
    var user = repository.findByUsername(request.getUsername())
            .orElseThrow();
    // Genera un nuevo token JWT para el usuario autenticado
    var jwtToken = jwtService.generateToken(user);
    // Revoca todos los tokens anteriores del usuario
    revokeAllUserTokens(user);
    // Guarda el nuevo token del usuario en el repositorio de tokens
    saveUserToken(user, jwtToken);
    // Crea y devuelve una respuesta de autenticación con el nuevo token JWT y otros detalles del usuario
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole())
            .build();
  }

  // Guarda el token de un usuario en el repositorio de tokens
  private void saveUserToken(Usuario user, String jwtToken) {
    var token = Token.builder()
            .usuario(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  // Revoca todos los tokens de un usuario
  protected void revokeAllUserTokens(Usuario user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

}
