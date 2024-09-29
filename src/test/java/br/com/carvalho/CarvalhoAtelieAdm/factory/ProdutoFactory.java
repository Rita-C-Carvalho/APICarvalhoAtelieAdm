package br.com.carvalho.CarvalhoAtelieAdm.factory;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.produto.ProdutoRequest;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;

import java.math.BigDecimal;

public class ProdutoFactory {

    public static ProdutoRequest produtoRequest(){
        ProdutoRequest produtoRequest = new ProdutoRequest();
        produtoRequest.setNomeProduto("Unicornio de amigurumi");
        produtoRequest.setDescricaoProduto("Unicornio de amigurumi");
        produtoRequest.setPrecoProduto(BigDecimal.valueOf(100.00));
        produtoRequest.setPorcentagemDesconto(BigDecimal.valueOf(10.00));
        produtoRequest.setDisponivel(true);
        return produtoRequest;
    }

    public static Produto produto(){
        Produto produto = new Produto();
        produto.setNomeProduto("Unicornio de amigurumi");
        produto.setDescricaoProduto("Unicornio de amigurumi");
        produto.setPrecoProduto(BigDecimal.valueOf(100.00));
        produto.setPorcentagemDesconto(BigDecimal.valueOf(10.00));
        produto.setImagemProduto1("imagem.png");
        produto.setIdProduto(1L);
        produto.setDisponivel(true);
        return produto;
    }
}
