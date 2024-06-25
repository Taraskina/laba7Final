package com.dar.server.main;

import com.dar.common.data.Request;
import com.dar.common.data.Response;
import com.dar.common.exceptions.NoAccountFounded;
import com.dar.server.data.entities.AccountEntity;
import com.dar.server.exceptions.InvalidPassword;
import com.dar.server.main.services.AuthService;
import lombok.Getter;
import lombok.Setter;

/**
 * менеджер клиента,упраявляющий его подключением,синхронизацией аккаунта с бд и авторизацией
 */
@Setter
@Getter
public class Client {
    @Getter
    boolean firstMessageFromClient = true;

    private AuthService authService;
    private AccountEntity user;

    public Client(AuthService authService) {
        this.authService = authService;
    }

    public void checkAnswer(Response response, Request request) throws InvalidPassword, NoAccountFounded {
        this.user =  authService.checkAnswer(response, request,this.user);

    }

}
