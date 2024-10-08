package com.byronxgomez.apiopenweathermapspringboot.controller;

import org.springframework.validation.BindingResult; // Importación de la clase BindingResult para manejar errores de validación

import java.util.HashMap;
import java.util.Map;

/**
 * Clase base para controladores en la aplicación.
 * Proporciona métodos y estructuras comunes para manejar mensajes de respuesta
 * y errores de validación.
 */
public class GenericController {

    // Map protegido para almacenar mensajes de respuesta
    protected Map<String, Object> mensaje;

    /**
     * Método para obtener un mapa de validaciones basado en los errores de un BindingResult.
     *
     * @param result el objeto BindingResult que contiene los errores de validación
     * @return un mapa que contiene los nombres de los campos como claves y los mensajes de error como valores
     */
    protected Map<String, Object> obtenerValidaciones(BindingResult result) {

        Map<String, Object> validaciones = new HashMap<>();

        // Iterar sobre los errores de campo y añadirlos al mapa de validaciones
        result.getFieldErrors()
                .forEach(error -> validaciones.put(error.getField(), error.getDefaultMessage()));


        return validaciones;
    }
}
