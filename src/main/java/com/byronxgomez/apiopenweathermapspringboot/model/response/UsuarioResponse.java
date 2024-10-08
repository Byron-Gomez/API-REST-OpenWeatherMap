package com.byronxgomez.apiopenweathermapspringboot.model.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse  {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
}
