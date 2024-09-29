package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.repository.ProdutoRepository;
import br.com.carvalho.CarvalhoAtelieAdm.factory.ProdutoFactory;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ListarProdutosServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ListarProdutosService listarProdutosService;

    private Pageable pageable;
    private ProdutoResponse produtoResponse;

    @BeforeEach
    void setUp() {
        pageable = mock(Pageable.class);
        Produto produto = ProdutoFactory.produto();
        produtoResponse = ProdutoResponse.builder()
                .idProduto(produto.getIdProduto())
                .nomeProduto(produto.getNomeProduto())
                .precoProduto(produto.getPrecoProduto())
                .porcentagemDesconto(produto.getPorcentagemDesconto())
                .precoComDesconto(produto.calcularValorComDesconto())
                .imagemProduto1(produto.getImagemProduto1())
                .disponivel(produto.isDisponivel())
                .build();
    }

    @Test
    @DisplayName("Deve listar todos os produtos paginados")
    void deveListarTodosProdutosPaginados() {
        Page<ProdutoResponse> expectedPage = new PageImpl<>(List.of(produtoResponse));
        when(produtoRepository.findAllProdutoResponses(pageable)).thenReturn(expectedPage);

        Page<ProdutoResponse> result = listarProdutosService.listarProdutos(pageable);

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(produtoRepository).findAllProdutoResponses(pageable);
    }

    @Test
    @DisplayName("Deve listar produtos disponíveis por nome paginados")
    void deveListarProdutosDisponiveisPorNomePaginados() {
        Page<ProdutoResponse> expectedPage = new PageImpl<>(List.of(produtoResponse));
        when(produtoRepository.findAvailableProductResponses(pageable)).thenReturn(expectedPage);

        Page<ProdutoResponse> result = listarProdutosService.listarProdutosDisponiveisPorNome(pageable);

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(produtoRepository).findAvailableProductResponses(pageable);
    }

    @Test
    @DisplayName("Deve listar produtos disponíveis por preço paginados")
    void deveListarProdutosDisponiveisPorPrecoPaginados() {
        Page<ProdutoResponse> expectedPage = new PageImpl<>(List.of(produtoResponse));
        when(produtoRepository.findAvailableProductResponsesPreco(pageable)).thenReturn(expectedPage);

        Page<ProdutoResponse> result = listarProdutosService.listarProdutosDisponiveisPorPreco(pageable);

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(produtoRepository).findAvailableProductResponsesPreco(pageable);
    }
}