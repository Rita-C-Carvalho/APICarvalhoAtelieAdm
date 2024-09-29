package br.com.carvalho.CarvalhoAtelieAdm.service.usuario;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.DetalharUsuarioResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Usuario;
import br.com.carvalho.CarvalhoAtelieAdm.mapper.usuario.DetalharUsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalharUsuarioService {

    private final BuscarUsuarioSecurityService buscarUsuarioSecurityService;

    @Autowired
    public DetalharUsuarioService(BuscarUsuarioSecurityService buscarUsuarioSecurityService) {
        this.buscarUsuarioSecurityService = buscarUsuarioSecurityService;
    }

    public DetalharUsuarioResponse detalharUsuario() {
        Usuario usuario = buscarUsuarioSecurityService.buscarUsuario();
        return DetalharUsuarioMapper.toResponse(usuario);
    }
}