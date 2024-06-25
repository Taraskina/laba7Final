package com.dar.server.main.services;

import com.dar.common.data.Request;
import com.dar.common.data.Response;
import com.dar.common.exceptions.NoAccountFounded;
import com.dar.server.data.entities.AccountEntity;
import com.dar.server.data.repositories.ServerAccountRepository;
import com.dar.server.exceptions.InvalidPassword;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final ServerAccountRepository serverAccountRepository;

    public AuthService(ServerAccountRepository serverAccountRepository) {
        this.serverAccountRepository = serverAccountRepository;
    }

    public AccountEntity checkAnswer(Response response, Request request, AccountEntity validUser) throws InvalidPassword, NoAccountFounded {
        if (!request.getCommandToExecute().getUser().equals(response.getUser())) {
            Optional<AccountEntity> optServerAccount = serverAccountRepository.get(response.getUser().getLogin());
            if (optServerAccount.isEmpty()) {
                throw new NoAccountFounded();
            } else {
                AccountEntity serverAccount = optServerAccount.get();
                if (serverAccount.getPassword().equals(response.getUser().getPassword())) {
                    return serverAccount;
                } else {
                    throw new InvalidPassword();
                }
            }
        }
        return validUser;
    }
}
