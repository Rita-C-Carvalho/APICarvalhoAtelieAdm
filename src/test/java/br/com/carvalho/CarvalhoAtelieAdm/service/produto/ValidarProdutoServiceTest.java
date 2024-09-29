package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import br.com.carvalho.CarvalhoAtelieAdm.exception.ProdutoNaoEncontradoException;
import br.com.carvalho.CarvalhoAtelieAdm.factory.ProdutoFactory;
import br.com.carvalho.CarvalhoAtelieAdm.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidarProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ValidarProdutoService validarProdutoService;

    private Produto produtoValido;

    @BeforeEach
    void setUp() {
        produtoValido = ProdutoFactory.produto();
    }

    @Test
    @DisplayName("Deve retornar produto quando ID existir")
    void deveRetornarProdutoQuandoIdExistir() {
        Long id = 1L;
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoValido));

        Produto resultado = validarProdutoService.validarEObterProduto(id);

        assertNotNull(resultado);
        assertEquals(produtoValido, resultado);
        verify(produtoRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto não for encontrado")
    void deveLancarExcecaoQuandoProdutoNaoForEncontrado() {
        Long id = 1L;
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        ProdutoNaoEncontradoException exception = assertThrows(ProdutoNaoEncontradoException.class,
                () -> validarProdutoService.validarEObterProduto(id));

        assertEquals("Produto não encontrado com o ID: " + id, exception.getMessage());
        verify(produtoRepository, times(1)).findById(id);
    }
}