package br.com.carvalho.CarvalhoAtelieAdm.mapper.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.DetalharProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;

public class DetalharProdutoMapper {
    public static DetalharProdutoResponse toResponse(Produto produto) {
        return DetalharProdutoResponse.builder()
                .nomeProduto(produto.getNomeProduto())
                .descricaoProduto(produto.getDescricaoProduto())
                .categoria(produto.getCategoria())
                .precoProduto(produto.getPrecoProduto())
                .precoComDesconto(produto.calcularValorComDesconto())
                .porcentagemDesconto(produto.getPorcentagemDesconto())
                .imagemProduto1(produto.getImagemProduto1())
                .imagemProduto2(produto.getImagemProduto2())
                .imagemProduto3(produto.getImagemProduto3())
                .imagemProduto4(produto.getImagemProduto4())
                .disponivel(produto.isDisponivel())
                .build();
    }
}
