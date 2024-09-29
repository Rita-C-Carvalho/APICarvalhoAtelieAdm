package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.factory.ProdutoResponseFactory;
import br.com.carvalho.CarvalhoAtelieAdm.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BuscarProdutosServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private BuscarProdutosService buscarProdutosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve buscar produtos com ordenação por nome")
    void buscarProdutos_OrdenacaoPorNome() {

        String nome = "Produto";
        String ordenacao = "nome";
        Pageable pageable = PageRequest.of(0, 10);

        List<ProdutoResponse> produtos = ProdutoResponseFactory.criarListaProdutoResponse();
        Page<ProdutoResponse> expectedPage = new PageImpl<>(produtos);

        when(produtoRepository.buscarProdutosPorNome(eq(nome), any(Pageable.class))).thenReturn(expectedPage);

        Page<ProdutoResponse> result = buscarProdutosService.buscarProdutos(nome, ordenacao, pageable);

        assertEquals(expectedPage, result);
        verify(produtoRepository).buscarProdutosPorNome(eq(nome), any(Pageable.class));
    }

    @Test
    @DisplayName("Deve buscar produtos com ordenação por preço")
    void buscarProdutos_OrdenacaoPorPreco() {
        // Arrange
        String nome = "Produto";
        String ordenacao = "preco";
        Pageable pageable = PageRequest.of(0, 10);

        List<ProdutoResponse> produtos = ProdutoResponseFactory.criarListaProdutoResponse();
        Page<ProdutoResponse> expectedPage = new PageImpl<>(produtos);

        when(produtoRepository.buscarProdutosPorNome(eq(nome), any(Pageable.class))).thenReturn(expectedPage);

        Page<ProdutoResponse> result = buscarProdutosService.buscarProdutos(nome, ordenacao, pageable);

        assertEquals(expectedPage, result);
        verify(produtoRepository).buscarProdutosPorNome(eq(nome), any(Pageable.class));
    }

    @Test
    @DisplayName("Deve buscar produtos com ordenação padrão quando ordenação é inválida")
    void buscarProdutos_OrdenacaoPadrao() {

        String nome = "Produto";
        String ordenacao = "invalidOrdenacao";
        Pageable pageable = PageRequest.of(0, 10);

        List<ProdutoResponse> produtos = ProdutoResponseFactory.criarListaProdutoResponse();
        Page<ProdutoResponse> expectedPage = new PageImpl<>(produtos);

        when(produtoRepository.buscarProdutosPorNome(eq(nome), any(Pageable.class))).thenReturn(expectedPage);

        Page<ProdutoResponse> result = buscarProdutosService.buscarProdutos(nome, ordenacao, pageable);

        assertEquals(expectedPage, result);
        verify(produtoRepository).buscarProdutosPorNome(eq(nome), any(Pageable.class));
    }
}