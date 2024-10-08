package com.byronxgomez.apiopenweathermapspringboot.controller;

import com.byronxgomez.apiopenweathermapspringboot.model.entity.Usuario;
import com.byronxgomez.apiopenweathermapspringboot.model.response.*;
import com.byronxgomez.apiopenweathermapspringboot.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para manejar las operaciones relacionadas con los usuarios.
 * Proporciona endpoints para obtener y actualizar información sobre usuarios.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Constructor que inicializa el servicio de usuarios.
     *
     * @param usuarioService instancia del servicio de usuarios
     */
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint para obtener todos los usuarios.
     *
     * @return ResponseEntity que contiene una lista de usuarios
     */
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<UsuarioResponse>> findAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAllUsuarios());
    }

    /**
     * Endpoint para actualizar un usuario por su ID.
     *
     * @param id el ID del usuario a actualizar
     * @param usuario el objeto Usuario que contiene los nuevos datos
     * @return ResponseEntity que contiene el usuario actualizado
     */
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Usuario> findUsuarioId(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.findUsuarioId(id, usuario));
    }

}
