package br.com.carvalho.CarvalhoAtelieAdm.controller.request.produto;

import br.com.carvalho.CarvalhoAtelieAdm.domain.Categoria;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ProdutoRequest {
    private String nomeProduto;
    private String descricaoProduto;
    private Categoria categoria;
    private BigDecimal precoProduto;
    private BigDecimal quantidadeProduto;
    private BigDecimal porcentagemDesconto;
    private String imagemProduto1;
    private String imagemProduto2;
    private String imagemProduto3;
    private String imagemProduto4;
    private boolean disponivel;
}
