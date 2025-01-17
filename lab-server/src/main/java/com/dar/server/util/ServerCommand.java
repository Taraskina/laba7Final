/**
 * Класс реализует паттерн command, и служит
 * для вызова разных команд в зависимости от того,что было введено в консоль
 */
package com.dar.server.util;


import com.dar.common.data.*;
import com.dar.common.exceptions.NoAccountFounded;
import com.dar.common.utilites.CommandType;
import com.dar.server.data.entities.AccountEntity;
import com.dar.server.data.repositories.CollectionRepository;
import com.dar.server.data.repositories.ServerAccountRepository;
import com.dar.server.data.repositories.PersonRepository;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static com.dar.common.utilites.JsonSerializer.toJson;

/**
 * реализация команды на сервере,аккаунт для исполнения у которой должен быть синхронизирован с бд
 */
@Setter
@Getter
public class ServerCommand implements Executable {
    public CommandType commandType;
    private AccountEntity user;
    private String[] args;
    private String value;
    private String name = "command";
    private CollectionRepository collectionRepository;
    private ServerAccountRepository serverAccountRepository;
    private PersonRepository spaceMarineRepository;

    public ServerCommand() {
    }


    public Response execute(CollectionRepository collectionRepository, ServerAccountRepository serverAccountRepository, PersonRepository spaceMarineRepository) {
        this.collectionRepository = collectionRepository;
        this.serverAccountRepository = serverAccountRepository;
        this.spaceMarineRepository = spaceMarineRepository;
        return calling();
    }

    public ServerCommand(String[] args, String value) {
        this.args = args;
        this.value = value;
    }

    public ServerCommand castInto(ServerCommand rightTtypeCommand, CommandDTO command) {
        ServerCommand answer;
        try {
            answer = rightTtypeCommand.getClass().getConstructor().newInstance();
            answer.setArgs(command.getArgs());
            answer.setName(command.getName());
            answer.setValue(command.getValue());
            System.out.println(toJson(command.getUser()));
            Optional<AccountEntity> optionalServerAccount = getServerAccountRepository().get(command.getUser().getLogin());
            if (optionalServerAccount.isPresent()) {
                answer.setUser(optionalServerAccount.get());
            } else {
                System.out.println("не нашлось ни одного пользователя с именем " + command.getUser().getLogin());
                throw new NoAccountFounded();
            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 NoAccountFounded e) {
            throw new RuntimeException(e);
        }
        return answer;
    }

    /**
     * метод,реализующий взаимодействие с коллекцией
     */


    @Override
    public Response calling() {
        Account user = this.user;
        Response resp = new Response();
        resp.setUser(new AccountDTO(user));
        resp.setSuccess(true);
        return resp;
    }
    ///


}
