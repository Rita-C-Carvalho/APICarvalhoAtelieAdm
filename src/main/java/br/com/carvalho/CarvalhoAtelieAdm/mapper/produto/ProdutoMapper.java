package br.com.carvalho.CarvalhoAtelieAdm.mapper.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.produto.ProdutoRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProdutoMapper {
    public static Produto toEntity(ProdutoRequest request) {
        Produto entity = new Produto();
        entity.setNomeProduto(request.getNomeProduto());
        entity.setDescricaoProduto(request.getDescricaoProduto());
        entity.setCategoria(request.getCategoria());
        entity.setPrecoProduto(request.getPrecoProduto());
        entity.setImagemProduto1(request.getImagemProduto1());
        entity.setImagemProduto2(request.getImagemProduto2());
        entity.setImagemProduto3(request.getImagemProduto3());
        entity.setImagemProduto4(request.getImagemProduto4());
        entity.setDisponivel(request.isDisponivel());
        entity.setQuantidadeProduto(request.getQuantidadeProduto());
        entity.setPorcentagemDesconto(request.getPorcentagemDesconto());
        return entity;
    }

    public static ProdutoResponse toResponse(Produto entity) {
        BigDecimal precoComDesconto = entity.calcularValorComDesconto();
        return ProdutoResponse.builder()
                .idProduto(entity.getIdProduto())
                .nomeProduto(entity.getNomeProduto())
                .categoria(entity.getCategoria())
                .precoProduto(entity.getPrecoProduto())
                .precoComDesconto(precoComDesconto.setScale(2, RoundingMode.HALF_UP))
                .porcentagemDesconto(entity.getPorcentagemDesconto())
                .imagemProduto1(entity.getImagemProduto1())
                .imagemProduto2(entity.getImagemProduto2())
                .imagemProduto3(entity.getImagemProduto3())
                .imagemProduto4(entity.getImagemProduto4())
                .disponivel(entity.isDisponivel())
                .build();
    }
}