package com.byronxgomez.apiopenweathermapspringboot.service;

import com.byronxgomez.apiopenweathermapspringboot.exception.ResourceNotFoundException;
import com.byronxgomez.apiopenweathermapspringboot.model.entity.Usuario;
import com.byronxgomez.apiopenweathermapspringboot.model.enums.Rol;
import com.byronxgomez.apiopenweathermapspringboot.model.response.*;
import com.byronxgomez.apiopenweathermapspringboot.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private  AuthenticationService authenticationService;


    public List<UsuarioResponse> findAllUsuarios(){
        List<Usuario>usuariosList= repository.findAll();
        List<UsuarioResponse> usuarioResponsesList=new ArrayList<>();
        usuariosList.forEach(usuario ->{
            UsuarioResponse usuarioResponse= UsuarioResponse.builder()
                    .id(usuario.getId())
                    .firstname(usuario.getFirstname())
                    .lastname(usuario.getLastname())
                    .email(usuario.getEmail())
                    .build();
            usuarioResponsesList.add(usuarioResponse);
        });
        return usuarioResponsesList;
    }
    public Usuario findUsuarioId(Integer id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User does not exist with id: " + id));
    }
    public Usuario findUsuarioId(Integer id,Usuario usuarioDetails){
        Usuario usuario = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User does not exist with id: " + id));
        usuario.setFirstname(usuarioDetails.getFirstname());
        usuario.setLastname(usuarioDetails.getLastname());
        usuario.setEmail(usuarioDetails.getEmail());
        return repository.save(usuario);
    }




}
