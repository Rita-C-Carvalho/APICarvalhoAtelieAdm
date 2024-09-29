package br.com.carvalho.CarvalhoAtelieAdm.controller.usuarioController;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.usuario.LoginRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.LoginResponse;
import br.com.carvalho.CarvalhoAtelieAdm.security.TokenBlacklistService;
import br.com.carvalho.CarvalhoAtelieAdm.security.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação de usuários")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Autentica um usuário e retorna um token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticateUser(loginRequest);
    }


    @PostMapping("/logout")
    @Operation(summary = "Logout de usuário", description = "Invalida o token JWT do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Token inválido")
    })
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            tokenBlacklistService.blacklistToken(jwtToken);
            return ResponseEntity.ok().body("Logout realizado com sucesso");
        }
        return ResponseEntity.badRequest().body("Token inválido");
    }
}