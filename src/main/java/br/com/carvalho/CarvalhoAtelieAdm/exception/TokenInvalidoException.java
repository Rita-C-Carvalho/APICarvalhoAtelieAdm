package br.com.carvalho.CarvalhoAtelieAdm.exception;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String message) {
        super(message);
    }
}