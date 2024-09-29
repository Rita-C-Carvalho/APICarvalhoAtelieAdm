package br.com.carvalho.CarvalhoAtelieAdm.service.produto;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para ImagemUploadService")
class ImagemUploadServiceTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @InjectMocks
    private ImagemUploadService imagemUploadService;

    @BeforeEach
    void setUp() {
        when(cloudinary.uploader()).thenReturn(uploader);
    }

    @Test
    @DisplayName("Deve fazer upload de imagem com sucesso")
    void deveFazerUploadDeImagemComSucesso() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile("test.jpg", "test.jpg", "image/jpeg", "test image content".getBytes());
        Map<String, Object> uploadResult = new HashMap<>();
        uploadResult.put("url", "https://res.cloudinary.com/demo/image/upload/v1573064775/sample.jpg");

        when(uploader.upload(any(byte[].class), eq(ObjectUtils.emptyMap()))).thenReturn(uploadResult);

        // Act
        String result = imagemUploadService.uploadImagem(file);

        // Assert
        assertNotNull(result);
        assertEquals("https://res.cloudinary.com/demo/image/upload/v1573064775/sample.jpg", result);
        verify(uploader, times(1)).upload(any(byte[].class), eq(ObjectUtils.emptyMap()));
    }

    @Test
    @DisplayName("Deve lançar IOException quando o upload falhar")
    void deveLancarIOExceptionQuandoUploadFalhar() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile("test.jpg", "test.jpg", "image/jpeg", "test image content".getBytes());
        when(uploader.upload(any(byte[].class), eq(ObjectUtils.emptyMap()))).thenThrow(new IOException("Upload failed"));

        // Act & Assert
        assertThrows(IOException.class, () -> imagemUploadService.uploadImagem(file));
        verify(uploader, times(1)).upload(any(byte[].class), eq(ObjectUtils.emptyMap()));
    }

    @Test
    @DisplayName("Deve lançar NullPointerException quando o arquivo for nulo")
    void deveLancarIllegalArgumentExceptionQuandoArquivoForNulo() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> imagemUploadService.uploadImagem(null));
        verifyNoInteractions(uploader);
    }
}