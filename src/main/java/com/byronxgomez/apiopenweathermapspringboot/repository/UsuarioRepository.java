package com.byronxgomez.apiopenweathermapspringboot.repository;

import com.byronxgomez.apiopenweathermapspringboot.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Método para buscar un usuario por su nombre de usuario
    Optional<Usuario> findByUsername(String username);
}
