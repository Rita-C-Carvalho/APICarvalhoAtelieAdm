package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.produto.ProdutoRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import br.com.carvalho.CarvalhoAtelieAdm.factory.ProdutoFactory;
import br.com.carvalho.CarvalhoAtelieAdm.mapper.produto.ProdutoMapper;
import br.com.carvalho.CarvalhoAtelieAdm.repository.ProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncluirProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private IncluirProdutoService incluirProdutoService;

    @Test
    @DisplayName("Deve incluir produto corretamente")
    void deveIncluirProdutoCorretamente() {
        // Arrange
        ProdutoRequest produtoRequest = ProdutoFactory.produtoRequest();
        produtoRequest.setImagemProduto1("imagem.png");

        Produto produtoSalvo = ProdutoFactory.produto();

        ProdutoResponse produtoResponse = new ProdutoResponse();
        produtoResponse.setNomeProduto(produtoSalvo.getNomeProduto());
        produtoResponse.setPrecoProduto(produtoSalvo.getPrecoProduto());
        produtoResponse.setImagemProduto1(produtoSalvo.getImagemProduto1());
        produtoResponse.setDisponivel(produtoSalvo.isDisponivel());

        try (MockedStatic<ProdutoMapper> mockedMapper = mockStatic(ProdutoMapper.class)) {
            mockedMapper.when(() -> ProdutoMapper.toEntity(produtoRequest)).thenReturn(produtoSalvo);
            mockedMapper.when(() -> ProdutoMapper.toResponse(produtoSalvo)).thenReturn(produtoResponse);

            when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvo);

            // Act
            ProdutoResponse resultado = incluirProdutoService.incluirProduto(produtoRequest);

            // Assert
            assertNotNull(resultado);
            assertEquals(produtoSalvo.getNomeProduto(), resultado.getNomeProduto());
            assertEquals(produtoSalvo.getPrecoProduto(), resultado.getPrecoProduto());
            assertEquals(produtoSalvo.getImagemProduto1(), resultado.getImagemProduto1());
            assertEquals(produtoSalvo.isDisponivel(), resultado.isDisponivel());

            verify(produtoRepository, times(1)).save(any(Produto.class));
            mockedMapper.verify(() -> ProdutoMapper.toEntity(produtoRequest), times(1));
            mockedMapper.verify(() -> ProdutoMapper.toResponse(produtoSalvo), times(1));
        }
    }

    @Test
    @DisplayName("Deve lançar exceção quando ProdutoRequest for nulo")
    void deveLancarExcecaoQuandoProdutoRequestForNulo() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> incluirProdutoService.incluirProduto(null));
        verifyNoInteractions(produtoRepository);
    }
}