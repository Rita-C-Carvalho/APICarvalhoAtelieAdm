package br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto;

import br.com.carvalho.CarvalhoAtelieAdm.domain.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponse {
    private Long idProduto;
    private String nomeProduto;
    private BigDecimal precoProduto;
    private Categoria categoria;
    private BigDecimal precoComDesconto;
    private BigDecimal porcentagemDesconto;
    private String imagemProduto1;
    private String imagemProduto2;
    private String imagemProduto3;
    private String imagemProduto4;
    private boolean disponivel;
}