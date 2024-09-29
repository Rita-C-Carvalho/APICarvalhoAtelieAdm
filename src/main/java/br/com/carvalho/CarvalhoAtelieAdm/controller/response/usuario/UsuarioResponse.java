package br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario;

import lombok.*;

@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String nomeCompleto;
    private String email;
}