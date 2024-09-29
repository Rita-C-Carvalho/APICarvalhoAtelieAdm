package br.com.carvalho.CarvalhoAtelieAdm.controller.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class UsuarioRequest {

    @NotBlank
    private String nomeCompleto;


    @Email
    @NotNull
    private String email;

    @NotBlank
    private String senha;


    private String imagemPerfil;

}
