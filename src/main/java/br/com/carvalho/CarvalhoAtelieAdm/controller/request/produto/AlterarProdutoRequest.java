package br.com.carvalho.CarvalhoAtelieAdm.controller.request.produto;

import br.com.carvalho.CarvalhoAtelieAdm.domain.Categoria;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class AlterarProdutoRequest {
    private String nomeProduto;
    private String descricaoProduto;
    private Categoria categoria;
    private BigDecimal precoProduto;
    private BigDecimal quantidadeProduto;
    private BigDecimal porcentagemDesconto;
    private boolean disponivel;
}