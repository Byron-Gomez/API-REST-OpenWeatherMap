package com.byronxgomez.apiopenweathermapspringboot.model.entity;

import com.byronxgomez.apiopenweathermapspringboot.model.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el valor de la clave primaria automáticamente
    private Integer id;

    @Column(unique = true) // Indica que este campo debe ser único en la base de datos
    private String token;

    @Enumerated(EnumType.STRING) // Especifica que el tipo de token se almacenará como una cadena
    private TokenType tokenType = TokenType.BEARER; // Tipo de token, por defecto es BEARER

    private boolean revoked; // Indica si el token ha sido revocado

    private boolean expired; // Indica si el token ha expirado

    @ManyToOne(fetch = FetchType.LAZY) // Relación ManyToOne con la entidad Usuario
    @JoinColumn(name = "user_id") // Define la columna de unión en la base de datos
    public Usuario usuario; // Usuario asociado al token


}
