package br.com.carvalho.CarvalhoAtelieAdm.mapper.usuario;


import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.DetalharUsuarioResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Usuario;

public class DetalharUsuarioMapper {
    public static DetalharUsuarioResponse toResponse(Usuario detalharUsuarioResponse) {
        return DetalharUsuarioResponse.builder()
                .id(detalharUsuarioResponse.getId())
                .nomeCompleto(detalharUsuarioResponse.getNomeCompleto())
                .email(detalharUsuarioResponse.getEmail())
                .imagemPerfil(detalharUsuarioResponse.getImagemPerfil())
                .build();
    }
}
