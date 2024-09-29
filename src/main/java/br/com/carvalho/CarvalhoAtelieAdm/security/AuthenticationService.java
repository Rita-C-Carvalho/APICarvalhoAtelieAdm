package br.com.carvalho.CarvalhoAtelieAdm.security;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.usuario.LoginRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.LoginResponse;
import br.com.carvalho.CarvalhoAtelieAdm.exception.SenhaIncorretaException;
import br.com.carvalho.CarvalhoAtelieAdm.exception.UsuarioNaoCadastradoException;
import br.com.carvalho.CarvalhoAtelieAdm.service.usuario.BuscarUsuarioSecurityService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final BuscarUsuarioSecurityService buscarUsuarioSecurityService;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtTokenProvider jwtTokenProvider,
                                 BuscarUsuarioSecurityService buscarUsuarioSecurityService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.buscarUsuarioSecurityService = buscarUsuarioSecurityService;
    }

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        try {
            buscarUsuarioSecurityService.buscarPorEmail(loginRequest.getEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));
            String token = jwtTokenProvider.createToken(authentication.getName());
            return new LoginResponse(token);
        } catch (UsuarioNaoCadastradoException e) {
            throw new UsuarioNaoCadastradoException("Usuário não cadastrado");
        } catch (BadCredentialsException e) {
            throw new SenhaIncorretaException("Senha incorreta");
        }
    }
}