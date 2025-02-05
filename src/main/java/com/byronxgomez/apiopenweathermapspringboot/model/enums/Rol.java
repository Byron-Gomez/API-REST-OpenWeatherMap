package com.byronxgomez.apiopenweathermapspringboot.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.byronxgomez.apiopenweathermapspringboot.model.enums.Permission.*;

@RequiredArgsConstructor
public enum Rol {
    USER(Collections.emptySet()),

    /**
     * Rol de administrador con permisos completos.
     */
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_CREATE

            )
    );

    @Getter
    private final Set<Permission> permissions;

    /**
     * Obtiene una lista de autoridades (granted authorities) asociadas al rol.
     * Incluye los permisos individuales y el rol como autoridades.
     *
     * @return Lista de autoridades asociadas al rol
     */
    public List<SimpleGrantedAuthority> getAuthorityList() {
        var authority = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authority.add(new SimpleGrantedAuthority("Role_" + this.name()));

        return authority;
    }
}
