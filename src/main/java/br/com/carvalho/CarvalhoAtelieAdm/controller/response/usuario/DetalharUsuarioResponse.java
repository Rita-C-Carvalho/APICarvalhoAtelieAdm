    package br.com.carvalho.CarvalhoAtelieAdm.controller.response.usuario;

    import lombok.*;

    @Getter @Setter
    @Builder @AllArgsConstructor @NoArgsConstructor
    public class DetalharUsuarioResponse {

        private Long id;
        private String nomeCompleto;
        private String email;
        private String imagemPerfil;
    }
