package br.com.carvalho.CarvalhoAtelieAdm.controller.usuarioController;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.usuario.UsuarioRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.DetalharUsuarioResponse;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario.UsuarioResponse;
import br.com.carvalho.CarvalhoAtelieAdm.service.usuario.BuscarUsuarioSecurityService;
import br.com.carvalho.CarvalhoAtelieAdm.service.usuario.DetalharUsuarioService;
import br.com.carvalho.CarvalhoAtelieAdm.service.usuario.IncluirUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioController {

    @Autowired
    private IncluirUsuarioService incluirUsuarioService;

    @Autowired
    private BuscarUsuarioSecurityService buscarUsuarioSecurityService;

    @Autowired
    private DetalharUsuarioService detalharUsuarioService;

    @PostMapping
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public UsuarioResponse incluir(@RequestBody UsuarioRequest request) {
        return incluirUsuarioService.criarUsuario(request);
    }

    @GetMapping("/me")
    @Operation(summary = "Buscar usuário logado", description = "Retorna os dados básicos do usuário atualmente autenticado")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do usuário retornados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public UsuarioResponse buscar() {
        return buscarUsuarioSecurityService.buscar();
    }

    @GetMapping("me/detalhar")
    @Operation(summary = "Detalhar usuário logado", description = "Retorna os dados detalhados do usuário atualmente autenticado")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados detalhados do usuário retornados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public DetalharUsuarioResponse detalharUsuario(){
        return detalharUsuarioService.detalharUsuario();
    }
}
