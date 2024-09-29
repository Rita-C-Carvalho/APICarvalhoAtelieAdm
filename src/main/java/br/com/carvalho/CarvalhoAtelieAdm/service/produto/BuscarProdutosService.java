package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BuscarProdutosService {

    private static final int TAMANHO_MINIMO_BUSCA = 3;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Page<ProdutoResponse> buscarProdutos(String nome, String ordenacao, Pageable pageable) {
        if (nome == null || nome.trim().length() < TAMANHO_MINIMO_BUSCA) {
            throw new IllegalArgumentException("A busca deve conter pelo menos " + TAMANHO_MINIMO_BUSCA + " caracteres.");
        }

        Sort sort;
        if ("preco".equalsIgnoreCase(ordenacao)) {
            sort = Sort.by("precoProduto");
        } else {
            sort = Sort.by("nomeProduto");
        }

        Pageable pageableWithSort = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        );

        return produtoRepository.buscarProdutosPorNome(nome.trim(), pageableWithSort);
    }
}