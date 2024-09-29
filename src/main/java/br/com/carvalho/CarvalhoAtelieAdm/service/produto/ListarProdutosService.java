package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarProdutosService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Page<ProdutoResponse> listarProdutos(Pageable pageable) {
        return produtoRepository.findAllProdutoResponses(pageable);
    }

    public Page<ProdutoResponse> listarProdutosDisponiveisPorNome(Pageable pageable) {
        return produtoRepository.findAvailableProductResponses(pageable);
    }

    public Page<ProdutoResponse> listarProdutosDisponiveisPorPreco(Pageable pageable) {
        return produtoRepository.findAvailableProductResponsesPreco(pageable);
    }
}