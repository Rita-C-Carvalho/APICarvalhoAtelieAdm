package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.produto.ProdutoRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import br.com.carvalho.CarvalhoAtelieAdm.mapper.produto.ProdutoMapper;
import br.com.carvalho.CarvalhoAtelieAdm.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncluirProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoResponse incluirProduto(ProdutoRequest produtoRequest) {
        Produto produto = ProdutoMapper.toEntity(produtoRequest);
        produto.setImagemProduto1(produtoRequest.getImagemProduto1());
        produto.setImagemProduto2(produtoRequest.getImagemProduto2());
        produto.setImagemProduto3(produtoRequest.getImagemProduto3());
        produto.setImagemProduto4(produtoRequest.getImagemProduto4());
        produtoRepository.save(produto);
        return ProdutoMapper.toResponse(produto);
    }
}