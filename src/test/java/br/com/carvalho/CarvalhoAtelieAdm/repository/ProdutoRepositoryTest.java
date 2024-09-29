package br.com.carvalho.CarvalhoAtelieAdm.repository;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Sql("/scripts/insert_produtos_teste.sql")
class ProdutoRepositoryIntegrationTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    @DisplayName("Deve encontrar produto por ID")
    void deveEncontrarProdutoPorId() {
        Optional<Produto> encontrado = produtoRepository.findById(1L);

        assertTrue(encontrado.isPresent());
        assertEquals("Produto 1", encontrado.get().getNomeProduto());
    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    void deveListarTodosProdutos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProdutoResponse> produtos = produtoRepository.findAllProdutoResponses(pageable);

        assertEquals(3, produtos.getTotalElements());
        assertTrue(produtos.getContent().stream().anyMatch(p -> p.getNomeProduto().equals("Produto 1")));
        assertTrue(produtos.getContent().stream().anyMatch(p -> p.getNomeProduto().equals("Produto 2")));
        assertTrue(produtos.getContent().stream().anyMatch(p -> p.getNomeProduto().equals("Produto 3")));
    }

    @Test
    @DisplayName("Deve listar produtos disponíveis ordenados por nome")
    void deveListarProdutosDisponiveisPorNome() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProdutoResponse> produtos = produtoRepository.findAvailableProductResponses(pageable);

        assertEquals(2, produtos.getTotalElements());
        assertEquals("Produto 1", produtos.getContent().get(0).getNomeProduto());
        assertEquals("Produto 2", produtos.getContent().get(1).getNomeProduto());
    }

    @Test
    @DisplayName("Deve listar produtos disponíveis ordenados por preço com desconto")
    void deveListarProdutosDisponiveisPorPreco() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProdutoResponse> produtos = produtoRepository.findAvailableProductResponsesPreco(pageable);

        assertEquals(2, produtos.getTotalElements());
        assertTrue(produtos.getContent().get(0).getPrecoComDesconto().compareTo(produtos.getContent().get(1).getPrecoComDesconto()) <= 0);
    }

    @Test
    @DisplayName("Deve retornar página vazia quando nenhum produto corresponder à busca")
    void deveRetornarPaginaVaziaQuandoNenhumProdutoCorresponder() {
        String nomeBusca = "ProdutoInexistente";
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProdutoResponse> resultado = produtoRepository.buscarProdutosPorNome(nomeBusca, pageable);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        assertEquals(0, resultado.getTotalElements());
    }

    @Test
    @DisplayName("Deve ordenar produtos por preço quando solicitado")
    void deveOrdenarProdutosPorPreco() {
        String nomeBusca = "Produto";
        String orderBy = "precoProduto";
        Pageable pageable = PageRequest.of(0, 10, Sort.by(orderBy));

        Page<ProdutoResponse> resultado = produtoRepository.buscarProdutosPorNome(nomeBusca, pageable);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.getContent().get(0).getPrecoProduto().compareTo(resultado.getContent().get(1).getPrecoProduto()) <= 0);
    }

}