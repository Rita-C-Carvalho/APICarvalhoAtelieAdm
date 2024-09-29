package br.com.carvalho.CarvalhoAtelieAdm.mapper.usuario;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.usuario.UsuarioRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.UsuarioResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Usuario;
import br.com.carvalho.CarvalhoAtelieAdm.factory.UsuarioFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    @Test
    @DisplayName("Deve mapear UserRequest para User corretamente")
    void toEntity() {
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        Usuario usuario = UsuarioMapper.toEntity(usuarioRequest);

        assertNotNull(usuario);
        assertEquals(usuarioRequest.getNomeCompleto(), usuario.getNomeCompleto());
        assertEquals(usuarioRequest.getEmail(), usuario.getEmail());
        assertEquals(usuarioRequest.getSenha(), usuario.getSenha());
        assertEquals(usuarioRequest.getImagemPerfil(), usuario.getImagemPerfil());
    }

    @Test
    @DisplayName("Deve mapear User para UserResponse corretamente")
    void toResponse() {
        Usuario usuario = UsuarioFactory.createUser();
        usuario.setId(1L);
        UsuarioResponse usuarioResponse = UsuarioMapper.toResponse(usuario);

        assertNotNull(usuarioResponse);
        assertEquals(usuarioResponse.getId(), usuario.getId());
        assertEquals(usuarioResponse.getNomeCompleto(), usuario.getNomeCompleto());
        assertEquals(usuarioResponse.getEmail(), usuario.getEmail());
    }
}