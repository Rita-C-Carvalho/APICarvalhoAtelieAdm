package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import br.com.carvalho.CarvalhoAtelieAdm.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletarProdutoService {

    @Autowired
    ValidarProdutoService validarProdutoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public void deletarProduto(Long produtoId) {
        Produto produto = validarProdutoService.validarEObterProduto(produtoId);
        produto.setDisponivel(false);
        produtoRepository.save(produto);
    }
}
