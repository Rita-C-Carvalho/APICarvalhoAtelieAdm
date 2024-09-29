package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.DetalharProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import br.com.carvalho.CarvalhoAtelieAdm.factory.ProdutoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetalharProdutoServiceTest {

    @Mock
    private ValidarProdutoService validarProdutoService;

    @InjectMocks
    private DetalharProdutoService detalharProdutoService;

    private Produto produtoMock;

    @BeforeEach
    void setUp() {
        produtoMock = ProdutoFactory.produto();
    }

    @Test
    @DisplayName("Deve detalhar produto com sucesso")
    void deveDetalharProdutoComSucesso() {
        // Arrange
        Long id = 1L;
        when(validarProdutoService.validarEObterProduto(id)).thenReturn(produtoMock);

        // Act
        DetalharProdutoResponse actualResponse = detalharProdutoService.detalharProduto(id);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(produtoMock.getNomeProduto(), actualResponse.getNomeProduto());
        assertEquals(produtoMock.getDescricaoProduto(), actualResponse.getDescricaoProduto());
        assertEquals(produtoMock.getPrecoProduto(), actualResponse.getPrecoProduto());
        assertEquals(produtoMock.getImagemProduto1(), actualResponse.getImagemProduto1());

        // Verify
        verify(validarProdutoService, times(1)).validarEObterProduto(id);
    }
}