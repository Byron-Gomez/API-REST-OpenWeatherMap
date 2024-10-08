package com.byronxgomez.apiopenweathermapspringboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    /**
     * Este método se invoca cuando un usuario intenta acceder a un recurso protegido
     * sin autenticación adecuada. Devuelve una respuesta HTTP 401 (No autorizado).
     *
     * @param request la solicitud HTTP que se ha recibido
     * @param response la respuesta HTTP que se enviará
     * @param authException la excepción de autenticación que se ha producido
     * @throws IOException si ocurre un error al escribir la respuesta
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        // Crear un map para almacenar la respuesta de error
        Map<String, Object> errorResponse = new HashMap<>();
        // Añadir un mensaje de error al mapa
        errorResponse.put("Acceso no permitido", "No tienes permiso para acceder a este recurso");

        // Establecer el estado de la respuesta a 401 (No autorizado)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // Establecer el tipo de contenido de la respuesta a JSON
        response.setContentType("application/json");
        // Escribir el mapa de error en el cuerpo de la respuesta como JSON
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
