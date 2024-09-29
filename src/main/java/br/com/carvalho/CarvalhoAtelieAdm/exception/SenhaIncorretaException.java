package br.com.carvalho.CarvalhoAtelieAdm.exception;

public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException(String message) {
        super(message);
    }
}