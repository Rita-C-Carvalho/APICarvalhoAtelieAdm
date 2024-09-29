package br.com.carvalho.CarvalhoAtelieAdm.factory;

import br.com.carvalho.CarvalhoAtelieAdm.domain.Usuario;

public class UsuarioFactory {
    public static Usuario createUser() {
        Usuario user = new Usuario();
        user.setNomeCompleto("Paula Carvalho");
        user.setEmail("paula@email.com");
        user.setImagemPerfil("http/imagem/prefil");
        user.setSenha("123456");
        return user;
    }
}
