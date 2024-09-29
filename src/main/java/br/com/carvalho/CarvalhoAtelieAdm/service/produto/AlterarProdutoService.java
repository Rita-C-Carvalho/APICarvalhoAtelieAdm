package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.produto.AlterarProdutoRequest;
import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import br.com.carvalho.CarvalhoAtelieAdm.mapper.produto.AlterarProdutoMapper;
import br.com.carvalho.CarvalhoAtelieAdm.mapper.produto.ProdutoMapper;
import br.com.carvalho.CarvalhoAtelieAdm.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlterarProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ValidarProdutoService validarProdutoService;

    @Transactional
    public ProdutoResponse alterarProduto(Long id, AlterarProdutoRequest request) {
        Produto produto = validarProdutoService.validarEObterProduto(id);

        AlterarProdutoMapper.updateEntityFromRequest(produto, request);

        Produto produtoAtualizado = produtoRepository.save(produto);
        return ProdutoMapper.toResponse(produtoAtualizado);
    }
}