package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.response.produto.ProdutoResponse;
import br.com.carvalho.CarvalhoAtelieAdm.domain.Produto;
import br.com.carvalho.CarvalhoAtelieAdm.exception.ProdutoNaoEncontradoException;
import br.com.carvalho.CarvalhoAtelieAdm.mapper.produto.ProdutoMapper;
import br.com.carvalho.CarvalhoAtelieAdm.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AlterarImagemProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ImagemUploadService imagemUploadService;

    public ProdutoResponse alterarImagensProduto(Long id, MultipartFile imagem1, MultipartFile imagem2, MultipartFile imagem3, MultipartFile imagem4) throws IOException {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado com o ID: " + id));

        if (imagem1 != null) {
            String novaImagemUrl1 = imagemUploadService.uploadImagem(imagem1);
            produto.setImagemProduto1(novaImagemUrl1);
        }
        if (imagem2 != null) {
            String novaImagemUrl2 = imagemUploadService.uploadImagem(imagem2);
            produto.setImagemProduto2(novaImagemUrl2);
        }
        if (imagem3 != null) {
            String novaImagemUrl3 = imagemUploadService.uploadImagem(imagem3);
            produto.setImagemProduto3(novaImagemUrl3);
        }
        if (imagem4 != null) {
            String novaImagemUrl4 = imagemUploadService.uploadImagem(imagem4);
            produto.setImagemProduto4(novaImagemUrl4);
        }

        Produto produtoAtualizado = produtoRepository.save(produto);
        return ProdutoMapper.toResponse(produtoAtualizado);
    }
}