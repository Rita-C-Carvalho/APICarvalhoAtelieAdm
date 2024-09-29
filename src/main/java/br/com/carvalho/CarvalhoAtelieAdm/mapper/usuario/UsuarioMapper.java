package br.com.carvalho.CarvalhoAtelieAdm.mapper.usuario;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.usuario.UsuarioRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.UsuarioResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Usuario;

public class UsuarioMapper {
    public static Usuario toEntity(UsuarioRequest request) {

        Usuario entity = new Usuario();
        entity.setNomeCompleto(request.getNomeCompleto());
        entity.setEmail(request.getEmail());
        entity.setSenha(request.getSenha());
        entity.setImagemPerfil(request.getImagemPerfil());
        return entity;
    }

    public static UsuarioResponse toResponse(Usuario entity) {
        return UsuarioResponse.builder()
                .id(entity.getId())
                .nomeCompleto(entity.getNomeCompleto())
                .email(entity.getEmail())
                .build();
    }
}
