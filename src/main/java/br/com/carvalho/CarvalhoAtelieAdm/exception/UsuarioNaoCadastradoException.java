package br.com.carvalho.CarvalhoAtelieAdm.exception;

public class UsuarioNaoCadastradoException extends RuntimeException {
    public UsuarioNaoCadastradoException(String message) {
        super(message);
    }
}