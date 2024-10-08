package com.byronxgomez.apiopenweathermapspringboot.controller;

import com.byronxgomez.apiopenweathermapspringboot.model.request.AuthenticationRequest;
import com.byronxgomez.apiopenweathermapspringboot.model.request.RegisterRequest;
import com.byronxgomez.apiopenweathermapspringboot.service.AuthenticationService;
import com.byronxgomez.apiopenweathermapspringboot.service.WeatherService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador que maneja las solicitudes relacionadas con la autenticación y registro de usuarios.
 * Las rutas base para todas las solicitudes en este controlador comienzan con "/api/v1/auth".
 */
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthenticationController extends GenericController{

    private final AuthenticationService service;
    private  WeatherService weatherService;
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    /**
     * Maneja la solicitud de registro de un nuevo usuario.
     *
     * @param request objeto de solicitud de registro
     * @return ResponseEntity con la respuesta de autenticación
     */

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, BindingResult result){
        mensaje = new HashMap<>();
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",obtenerValidaciones(result));
            return ResponseEntity.unprocessableEntity().body(mensaje);
        }
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("mensaje",service.register(request));
        return ResponseEntity.ok(mensaje);
    }

    /**
     * Maneja la solicitud de autenticación de un usuario.
     *
     * @param request objeto de solicitud de autenticación
     * @return ResponseEntity con la respuesta de autenticación
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest request,BindingResult result) {
        mensaje= new HashMap<>();
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",obtenerValidaciones(result));
            return ResponseEntity.unprocessableEntity().body(mensaje);
        }
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("mensaje",service.authenticate(request));
        return ResponseEntity.ok(mensaje);
    }


    }


