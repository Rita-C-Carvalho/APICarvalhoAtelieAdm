package br.com.carvalho.CarvalhoAtelieAdm.mapper.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.produto.ProdutoRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import br.com.carvalho.CarvalhoAtelieAdm.factory.ProdutoFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoMapperTest {

    @Test
    @DisplayName("Deve mapear ProdutoRequest corretamente")
    void toEntity() {
        ProdutoRequest produtoRequest = ProdutoFactory.produtoRequest();

        Produto produto = ProdutoMapper.toEntity(produtoRequest);

        assertNotNull(produto);
        assertEquals(produtoRequest.getNomeProduto(), produto.getNomeProduto());
        assertEquals(produtoRequest.getDescricaoProduto(), produto.getDescricaoProduto());
        assertEquals(produtoRequest.getPrecoProduto(), produto.getPrecoProduto());
        assertEquals(produtoRequest.getImagemProduto1(), produto.getImagemProduto1());
        assertEquals(produtoRequest.isDisponivel(), produto.isDisponivel());
    }

    @Test
    @DisplayName("Deve mapear Produto para ProdutoResponse corretamente")
    void toResponse() {

        Produto produto = ProdutoFactory.produto();
        ProdutoResponse produtoResponse = ProdutoMapper.toResponse(produto);

        assertNotNull(produtoResponse);
        assertEquals(produtoResponse.getNomeProduto(), produto.getNomeProduto());
        assertEquals(produtoResponse.getPrecoProduto(), produto.getPrecoProduto());
        assertEquals(produtoResponse.getImagemProduto1(), produto.getImagemProduto1());
        assertEquals(produtoResponse.isDisponivel(), produto.isDisponivel());

    }
}