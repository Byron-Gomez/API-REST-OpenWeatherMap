package com.byronxgomez.apiopenweathermapspringboot.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeración que define los permisos disponibles en el sistema.
 */
@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),            // Permiso de lectura para el administrador
    ADMIN_UPDATE("admin:update"),        // Permiso de actualización para el administrador
    ADMIN_CREATE("admin:create"),        // Permiso de creación para el administrador

    ;

    @Getter
    private final String permission;
}
