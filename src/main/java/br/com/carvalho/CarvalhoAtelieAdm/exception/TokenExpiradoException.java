package br.com.carvalho.CarvalhoAtelieAdm.exception;

public class TokenExpiradoException extends RuntimeException {
    public TokenExpiradoException(String message) {
        super(message);
    }
}