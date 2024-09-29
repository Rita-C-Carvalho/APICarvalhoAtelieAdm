package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import br.com.carvalho.CarvalhoAtelieAdm.repository.ProdutoRepository;
import br.com.carvalho.CarvalhoAtelieAdm.factory.ProdutoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeletarProdutoServiceTest {

    @Mock
    private ValidarProdutoService validarProdutoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private DeletarProdutoService deletarProdutoService;

    private Produto produto;
    private Long produtoId;

    @BeforeEach
    void setUp() {
        produto = ProdutoFactory.produto();
        produtoId = produto.getIdProduto();
    }

    @Test
    @DisplayName("Deve deletar (desativar) um produto com sucesso")
    void deveDeletarProdutoComSucesso() {
        when(validarProdutoService.validarEObterProduto(produtoId)).thenReturn(produto);

        deletarProdutoService.deletarProduto(produtoId);

        assertFalse(produto.isDisponivel());
        verify(validarProdutoService).validarEObterProduto(produtoId);
        verify(produtoRepository).save(produto);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o produto não for encontrado")
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        when(validarProdutoService.validarEObterProduto(produtoId))
                .thenThrow(new RuntimeException("Produto não encontrado"));

        assertThrows(RuntimeException.class, () -> {
            deletarProdutoService.deletarProduto(produtoId);
        });

        verify(validarProdutoService).validarEObterProduto(produtoId);
        verify(produtoRepository, never()).save(any());
    }
}