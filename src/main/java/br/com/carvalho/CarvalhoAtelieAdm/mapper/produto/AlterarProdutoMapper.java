package br.com.carvalho.CarvalhoAtelieAdm.mapper.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.produto.AlterarProdutoRequest;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;

public class AlterarProdutoMapper {
    public static void updateEntityFromRequest(Produto produto, AlterarProdutoRequest request) {
        produto.setNomeProduto(request.getNomeProduto());
        produto.setDescricaoProduto(request.getDescricaoProduto());
        produto.setCategoria(request.getCategoria());
        produto.setPrecoProduto(request.getPrecoProduto());
        produto.setQuantidadeProduto(request.getQuantidadeProduto());
        produto.setPorcentagemDesconto(request.getPorcentagemDesconto());
        produto.setDisponivel(request.isDisponivel());
    }
}