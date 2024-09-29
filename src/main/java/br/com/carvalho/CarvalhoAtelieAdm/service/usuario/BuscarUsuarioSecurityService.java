package br.com.carvalho.CarvalhoAtelieAdm.service.usuario;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.UsuarioResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Usuario;
import br.com.carvalho.CarvalhoAtelieAdm.exception.UsuarioNaoCadastradoException;
import br.com.carvalho.CarvalhoAtelieAdm.exception.UsuarioNaoEncontradoException;
import br.com.carvalho.CarvalhoAtelieAdm.mapper.usuario.UsuarioMapper;
import br.com.carvalho.CarvalhoAtelieAdm.repository.UsuarioRepository;
import br.com.carvalho.CarvalhoAtelieAdm.security.util.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class BuscarUsuarioSecurityService {

    private final UsuarioRepository usuarioRepository;

    public BuscarUsuarioSecurityService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse buscar() {
        String email = SecurityUtils.getEmailUsuarioLogado();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        return UsuarioMapper.toResponse(usuario);
    }

    public Usuario buscarUsuario() {
        String email = SecurityUtils.getEmailUsuarioLogado();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não autenticado"));
    }

    public void buscarPorEmail(String email) {
        if (!usuarioRepository.existsByEmail(email)) {
            throw new UsuarioNaoCadastradoException("Usuário não cadastrado");
        }
    }
}