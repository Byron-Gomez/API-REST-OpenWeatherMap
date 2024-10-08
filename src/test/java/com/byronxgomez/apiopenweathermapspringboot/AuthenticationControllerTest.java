package com.byronxgomez.apiopenweathermapspringboot;

import com.byronxgomez.apiopenweathermapspringboot.controller.AuthenticationController;
import com.byronxgomez.apiopenweathermapspringboot.model.request.AuthenticationRequest;
import com.byronxgomez.apiopenweathermapspringboot.model.request.RegisterRequest;
import com.byronxgomez.apiopenweathermapspringboot.model.response.AuthenticationResponse;
import com.byronxgomez.apiopenweathermapspringboot.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    // Mockea el servicio de autenticación
    @Mock
    private AuthenticationService authenticationService;

    // Mockea el resultado de validaciones
    @Mock
    private BindingResult bindingResult;

    // Map para almacenar mensajes de respuesta
    private Map<String, Object> mensaje;

    // Inicializa los mocks y el mapa de mensajes antes de cada prueba
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
        mensaje = new HashMap<>();
    }

    // Prueba que la función register devuelva una respuesta exitosa
    @Test
    public void testRegisterSuccess() {
        RegisterRequest request = new RegisterRequest(); // Crea una solicitud de registro
        request.setUsername("testUser"); // se tiene que usar un user ya creado
        request.setPassword("testPassword");

        AuthenticationResponse mockResponse = new AuthenticationResponse(); // Mock de la respuesta

        // Simula que no hay errores en la validación
        when(bindingResult.hasErrors()).thenReturn(false);

        // Simula la respuesta del servicio de autenticación
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(mockResponse);

        // Llama al método register del controlador
        ResponseEntity<?> response = authenticationController.register(request, bindingResult);

        // Verifica que la respuesta sea correcta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Boolean.TRUE, ((Map<?, ?>) response.getBody()).get("success"));
        assertEquals("User registered successfully", ((Map<?, ?>) response.getBody()).get("mensaje"));
    }

    // Prueba que la función register devuelva una respuesta de error si hay fallos de validación
    @Test
    public void testRegisterFailure() {
        when(bindingResult.hasErrors()).thenReturn(true); // Simula que hay errores de validación
        when(bindingResult.getFieldError()).thenReturn(null); // Simula un error específico

        // Llama al método register del controlador con una solicitud vacía
        ResponseEntity<?> response = authenticationController.register(new RegisterRequest(), bindingResult);

        // Verifica que la respuesta sea de error (422 UNPROCESSABLE_ENTITY)
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(Boolean.FALSE, ((Map<?, ?>) response.getBody()).get("success"));
    }

    // Prueba que la función authenticate devuelva una respuesta exitosa
    @Test
    public void testAuthenticateSuccess() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testUser");
        request.setPassword("testPassword");

        AuthenticationResponse mockResponse = new AuthenticationResponse();

        // Simula que no hay errores en la validación
        when(bindingResult.hasErrors()).thenReturn(false);

        // Simula la respuesta del servicio de autenticación
        when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenReturn(mockResponse);

        // Llama al método authenticate del controlador
        ResponseEntity<?> response = authenticationController.authenticate(request, bindingResult);

        // Verifica que la respuesta sea correcta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Boolean.TRUE, ((Map<?, ?>) response.getBody()).get("success"));
        assertEquals("User authenticated successfully", ((Map<?, ?>) response.getBody()).get("mensaje"));
    }

    // Prueba que la función authenticate devuelva una respuesta de error si hay fallos de validación
    @Test
    public void testAuthenticateFailure() {
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldError()).thenReturn(null);

        // Llama al método authenticate del controlador con una solicitud vacía
        ResponseEntity<?> response = authenticationController.authenticate(new AuthenticationRequest(), bindingResult);

        // Verifica que la respuesta sea de error (422 UNPROCESSABLE_ENTITY)
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(Boolean.FALSE, ((Map<?, ?>) response.getBody()).get("success"));
    }
}
