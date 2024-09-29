package br.com.carvalho.CarvalhoAtelieAdm.factory;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Categoria;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ProdutoResponseFactory {

    public static ProdutoResponse criarProdutoResponse(Long id, String nome, BigDecimal preco, Categoria categoria) {
        BigDecimal desconto = BigDecimal.valueOf(10);
        BigDecimal precoComDesconto = preco.subtract(preco.multiply(desconto).divide(BigDecimal.valueOf(100)));

        return ProdutoResponse.builder()
                .idProduto(id)
                .nomeProduto(nome)
                .precoProduto(preco)
                .categoria(categoria)
                .precoComDesconto(precoComDesconto)
                .porcentagemDesconto(desconto)
                .imagemProduto1("imagem.jpg")
                .disponivel(true)
                .build();
    }

    public static List<ProdutoResponse> criarListaProdutoResponse() {
        return Arrays.asList(
                criarProdutoResponse(1L, "Produto A", new BigDecimal("100.00"), Categoria.BONECAS),
                criarProdutoResponse(2L, "Produto B", new BigDecimal("200.00"), Categoria.ANIMAIS)
        );
    }
}