package br.com.carvalho.CarvalhoAtelieAdm.service.usuario;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.usuario.UsuarioRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.UsuarioResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Usuario;
import br.com.carvalho.CarvalhoAtelieAdm.mapper.usuario.UsuarioMapper;
import br.com.carvalho.CarvalhoAtelieAdm.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IncluirUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public IncluirUsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse criarUsuario(UsuarioRequest usuarioRequest) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioRequest);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return UsuarioMapper.toResponse(usuarioSalvo);
    }
}