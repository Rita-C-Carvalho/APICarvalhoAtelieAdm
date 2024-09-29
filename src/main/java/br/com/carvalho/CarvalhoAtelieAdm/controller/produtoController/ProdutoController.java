package br.com.carvalho.CarvalhoAtelieAdm.controller.produtoController;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.produto.AlterarProdutoRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.request.produto.ProdutoRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.DetalharProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.service.produto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "API de gerenciamento de produtos")
public class ProdutoController {

    @Autowired
    private IncluirProdutoService incluirProdutoService;

    @Autowired
    private ProdutoImagemService produtoImagemService;

    @Autowired
    private ListarProdutosService listarProdutosService;

    @Autowired
    private DeletarProdutoService deletarProdutoService;

    @Autowired
    private DetalharProdutoService detalharProdutoService;

    @Autowired
    private BuscarProdutosService buscarProdutosService;

    @Autowired
    private AlterarProdutoService alterarProdutoService;

    @Autowired
    private AlterarImagemProdutoService alterarImagemProdutoService;

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Incluir novo produto", description = "Adiciona um novo produto com imagens")
    public ProdutoResponse incluirProduto(
            @RequestPart("produto") String produtoJson,
            @RequestPart("imagens") MultipartFile[] imagens) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProdutoRequest produtoRequest = objectMapper.readValue(produtoJson, ProdutoRequest.class);

        // Uso do serviço para validar e fazer upload das imagens
        produtoImagemService.validarEUploadImagens(produtoRequest, imagens);

        return incluirProdutoService.incluirProduto(produtoRequest);
    }

    @GetMapping("/detalhar/{id}")
    @Operation(summary = "Detalhar produto", description = "Retorna os detalhes de um produto específico")
    public DetalharProdutoResponse detalharProduto(@PathVariable Long id) {
        return detalharProdutoService.detalharProduto(id);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar produtos", description = "Retorna uma lista paginada com todos os produtos")
    public ResponseEntity<Page<ProdutoResponse>> listarProdutos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProdutoResponse> produtos = listarProdutosService.listarProdutos(pageRequest);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/listar-disponiveis-nome")
    @Operation(summary = "Listar produtos disponíveis", description = "Retorna uma lista paginada com os produtos disponíveis, ordenados por nome")
    public ResponseEntity<Page<ProdutoResponse>> listarProdutosDisponiveisPorNome(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProdutoResponse> produtosDisponiveis = listarProdutosService.listarProdutosDisponiveisPorNome(pageRequest);
        return ResponseEntity.ok(produtosDisponiveis);
    }

    @GetMapping("/listar-disponiveis-preco")
    @Operation(summary = "Listar produtos disponíveis", description = "Retorna uma lista paginada com os produtos disponíveis, ordenados por preço")
    public ResponseEntity<Page<ProdutoResponse>> listarProdutosDisponiveisPorPreco(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProdutoResponse> produtosDisponiveis = listarProdutosService.listarProdutosDisponiveisPorPreco(pageRequest);
        return ResponseEntity.ok(produtosDisponiveis);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/deletar/{produtoId}")
    @Operation(summary = "Deletar produto", description = "Deleta um produto com exclusão lógica, colocando a disponibilidade como false")
    public void deletarProduto(@PathVariable Long produtoId) {
        deletarProdutoService.deletarProduto(produtoId);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar produtos pelo nome", description = "Retorna uma lista paginada com os produtos disponíveis, de acordo com o nome digitado, a ordem default é por nome, porém pode ser ordenado também por preço.")
    public ResponseEntity<Page<ProdutoResponse>> buscarProdutos(
            @RequestParam String nome,
            @RequestParam(defaultValue = "nome") String ordenacao,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProdutoResponse> produtos = buscarProdutosService.buscarProdutos(nome, ordenacao, pageRequest);
        return ResponseEntity.ok(produtos);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    @Operation(summary = "Alterar produto", description = "Altera um produto existente")
    public ResponseEntity<ProdutoResponse> alterarProduto(
            @PathVariable Long id,
            @RequestBody AlterarProdutoRequest request) {
        ProdutoResponse produtoAtualizado = alterarProdutoService.alterarProduto(id, request);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/{id}/imagens", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Alterar imagens do produto", description = "Atualiza as imagens de um produto existente")
    public ResponseEntity<ProdutoResponse> alterarImagensProduto(
            @PathVariable Long id,
            @RequestPart(value = "imagem1", required = false) MultipartFile imagem1,
            @RequestPart(value = "imagem2", required = false) MultipartFile imagem2,
            @RequestPart(value = "imagem3", required = false) MultipartFile imagem3,
            @RequestPart(value = "imagem4", required = false) MultipartFile imagem4) {
        try {
            ProdutoResponse produtoAtualizado = alterarImagemProdutoService.alterarImagensProduto(id, imagem1, imagem2, imagem3, imagem4);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}