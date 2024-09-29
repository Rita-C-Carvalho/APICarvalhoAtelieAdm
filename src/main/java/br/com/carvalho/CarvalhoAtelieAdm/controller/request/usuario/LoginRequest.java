package br.com.carvalho.CarvalhoAtelieAdm.controller.request.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String senha;
}