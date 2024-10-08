package com.byronxgomez.apiopenweathermapspringboot.security;

import com.byronxgomez.apiopenweathermapspringboot.controller.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;
   private final CustomAuthenticationEntryPoint authenticationEntryPoint;

  // Configura la cadena de filtros de seguridad
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      try {
          http
                  .csrf()
                  .disable()
                  .authorizeHttpRequests()
                  .requestMatchers( // Permitir el acceso público a estas rutas específicas sin autenticación
                          "/api/v1/auth/**",
                          "/swagger-ui.html/**",
                          "/v3/api-docs/**",
                          "/swagger-resources/**",
                          "/swagger-ui/**",
                          "/configuration/ui",
                          "/configuration/security",
                          "/webjars/**",
                          "/swagger-ui.html",
                          "/api/v1/weather/**"
                          )
                  .permitAll()
                  .anyRequest() // Restringir el acceso a otras rutas, requerir autenticación
                  .authenticated()
                  .and()
                  .sessionManagement()
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                  .and()
                  .authenticationProvider(authenticationProvider)
                  .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)// Agregar el filtro de autenticación JWT antes del filtro de autenticación de usuario y contraseña
                  .logout()// Configurar la funcionalidad de cierre de sesión
                  .logoutUrl("/api/v1/auth/logout")
                  .addLogoutHandler(logoutHandler)
                  .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                  .and() //Controla las excepciones
                  .exceptionHandling()
                  .authenticationEntryPoint(authenticationEntryPoint)
          ;
      } catch (Exception e) {
          System.out.println("No se puede establecer la autenticación del usuario"+e.toString());
        }
      return http.build();
  }

}
