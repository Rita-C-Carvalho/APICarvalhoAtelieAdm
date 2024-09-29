package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.DetalharProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import br.com.carvalho.CarvalhoAtelieAdm.mapper.produto.DetalharProdutoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalharProdutoService {

    @Autowired
    private ValidarProdutoService validarProdutoService;

    public DetalharProdutoResponse detalharProduto(Long id) {
        Produto produto = validarProdutoService.validarEObterProduto(id);
        return DetalharProdutoMapper.toResponse(produto);
    }
}