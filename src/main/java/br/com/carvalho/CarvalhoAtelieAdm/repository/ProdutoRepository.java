package br.com.carvalho.CarvalhoAtelieAdm.repository;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findById(Long id);

    @Query("SELECT new br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse(" +
            "p.idProduto, p.nomeProduto, p.precoProduto, p.categoria, " +
            "FUNCTION('ROUND', p.precoProduto * (1 - p.porcentagemDesconto / 100), 2), " +
            "p.porcentagemDesconto, p.imagemProduto1, p.imagemProduto2, p.imagemProduto3," +
            "p.imagemProduto4, p.disponivel) " +
            "FROM Produto p ORDER BY p.disponivel DESC")
    Page<ProdutoResponse> findAllProdutoResponses(Pageable pageable);

    @Query("SELECT new br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse(" +
            "p.idProduto, p.nomeProduto, p.precoProduto, p.categoria, " +
            "FUNCTION('ROUND', p.precoProduto * (1 - p.porcentagemDesconto / 100), 2), " +
            "p.porcentagemDesconto, p.imagemProduto1, p.imagemProduto2, p.imagemProduto3," +
            "p.imagemProduto4, p.disponivel) " +
            "FROM Produto p WHERE p.disponivel = true ORDER BY p.nomeProduto ASC")
    Page<ProdutoResponse> findAvailableProductResponses(Pageable pageable);

    @Query("SELECT new br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse(" +
            "p.idProduto, p.nomeProduto, p.precoProduto, p.categoria, " +
            "FUNCTION('ROUND', p.precoProduto * (1 - p.porcentagemDesconto / 100), 2), " +
            "p.porcentagemDesconto, p.imagemProduto1, p.imagemProduto2, p.imagemProduto3," +
            "p.imagemProduto4, p.disponivel) " +
            "FROM Produto p WHERE p.disponivel = true ORDER BY p.precoProduto * (1 - p.porcentagemDesconto / 100) ASC")
    Page<ProdutoResponse> findAvailableProductResponsesPreco(Pageable pageable);

    @Query("SELECT new br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse(" +
            "p.idProduto, p.nomeProduto, p.precoProduto, p.categoria, " +
            "FUNCTION('ROUND', p.precoProduto * (1 - p.porcentagemDesconto / 100), 2), " +
            "p.porcentagemDesconto, p.imagemProduto1, p.imagemProduto2, p.imagemProduto3," +
            "p.imagemProduto4, " +
            "p.disponivel) " +
            "FROM Produto p " +
            "WHERE p.disponivel = true AND LOWER(p.nomeProduto) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<ProdutoResponse> buscarProdutosPorNome(String nome, Pageable pageable);

}