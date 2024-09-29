package br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto;

import br.com.carvalho.CarvalhoAtelieAdm.domain.Categoria;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class DetalharProdutoResponse {
    private String nomeProduto;
    private String descricaoProduto;
    private Categoria categoria;
    private BigDecimal precoProduto;
    private BigDecimal precoComDesconto;
    private BigDecimal porcentagemDesconto;
    private String imagemProduto1;
    private String imagemProduto2;
    private String imagemProduto3;
    private String imagemProduto4;
    private boolean disponivel;
}
