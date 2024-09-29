package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import br.com.carvalho.CarvalhoAtelieAdm.controller.request.produto.ProdutoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProdutoImagemService {

    @Autowired
    private ImagemUploadService imagemUploadService;

    public void validarEUploadImagens(ProdutoRequest produtoRequest, MultipartFile[] imagens) throws IOException {
        if (imagens.length < 4) {
            throw new IllegalArgumentException("Devem ser fornecidas pelo menos 4 imagens.");
        }

        produtoRequest.setImagemProduto1(imagemUploadService.uploadImagem(imagens[0]));
        produtoRequest.setImagemProduto2(imagemUploadService.uploadImagem(imagens[1]));
        produtoRequest.setImagemProduto3(imagemUploadService.uploadImagem(imagens[2]));
        produtoRequest.setImagemProduto4(imagemUploadService.uploadImagem(imagens[3]));
    }
}