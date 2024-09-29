package br.com.carvalho.CarvalhoAtelieAdm.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;
    private String nomeProduto;
    private String descricaoProduto;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private BigDecimal precoProduto;
    private BigDecimal quantidadeProduto;

    @Column(columnDefinition = "DECIMAL(19,2) DEFAULT 0.00")
    @Builder.Default
    private BigDecimal porcentagemDesconto = BigDecimal.ZERO;

    private String imagemProduto1;
    private String imagemProduto2;
    private String imagemProduto3;
    private String imagemProduto4;
    private boolean disponivel = true;

    // Método para calcular o valor com desconto
    public BigDecimal calcularValorComDesconto() {
        if (porcentagemDesconto != null && porcentagemDesconto.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal fatorDesconto = BigDecimal.ONE.subtract(porcentagemDesconto.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
            return precoProduto.multiply(fatorDesconto).setScale(2, RoundingMode.HALF_UP);
        }
        return precoProduto;
    }
}