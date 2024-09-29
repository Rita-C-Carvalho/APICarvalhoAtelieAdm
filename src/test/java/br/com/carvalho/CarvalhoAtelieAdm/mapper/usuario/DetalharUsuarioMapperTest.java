package br.com.carvalho.CarvalhoAtelieAdm.mapper.usuario;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.DetalharUsuarioResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Usuario;
import br.com.carvalho.CarvalhoAtelieAdm.factory.UsuarioFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetalharUsuarioMapperTest {

    @Test
    @DisplayName("Deve mapear User para UserResponse corretamente")
    void toResponse() {
        Usuario usuario = UsuarioFactory.createUser();
        usuario.setId(1L);
        DetalharUsuarioResponse detalharUsuarioResponse = DetalharUsuarioMapper.toResponse(usuario);

        assertNotNull(detalharUsuarioResponse);
        assertEquals(detalharUsuarioResponse.getId(), usuario.getId());
        assertEquals(detalharUsuarioResponse.getNomeCompleto(), usuario.getNomeCompleto());
        assertEquals(detalharUsuarioResponse.getEmail(), usuario.getEmail());
        assertEquals(detalharUsuarioResponse.getImagemPerfil(), usuario.getImagemPerfil());
    }
}