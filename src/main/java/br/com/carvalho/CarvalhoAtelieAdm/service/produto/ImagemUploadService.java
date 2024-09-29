package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImagemUploadService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImagem(MultipartFile file) throws IOException {
        var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("url");
    }
}