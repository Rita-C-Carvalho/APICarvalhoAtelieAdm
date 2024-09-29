package br.com.carvalho.CarvalhoAtelieAdm.mapper.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.DetalharProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import br.com.carvalho.CarvalhoAtelieAdm.factory.ProdutoFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetalharProdutoMapperTest {

    @Test
    @DisplayName("Deve mapear Produto para DetalharProdutoResponse corretamente")
    void toResponse() {

        Produto produto = ProdutoFactory.produto();
        DetalharProdutoResponse detalharProdutoResponse = DetalharProdutoMapper.toResponse(produto);

        assertNotNull(detalharProdutoResponse);
        assertEquals(detalharProdutoResponse.getNomeProduto(), produto.getNomeProduto());
        assertEquals(detalharProdutoResponse.getDescricaoProduto(), produto.getDescricaoProduto());
        assertEquals(detalharProdutoResponse.getPrecoProduto(), produto.getPrecoProduto());
        assertEquals(detalharProdutoResponse.getImagemProduto1(), produto.getImagemProduto1());
        assertEquals(detalharProdutoResponse.isDisponivel(), produto.isDisponivel());

    }
}