package com.dar.server.main.handlers;

import com.dar.common.data.Request;
import com.dar.common.data.Response;
import com.dar.common.exceptions.NoAccountFounded;
import com.dar.server.data.repositories.CollectionRepository;
import com.dar.server.data.repositories.ServerAccountRepository;
import com.dar.server.data.repositories.PersonRepository;
import com.dar.server.exceptions.InvalidPassword;
import com.dar.server.main.ClientConnector;
import com.dar.server.main.services.CommandExtractorService;
import com.dar.server.util.ServerCommand;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;



/**
 * класс,обрабатывающий запрос в отдельном потоке и создающий ответное сообщение
 */

public class RequestHandler implements Callable<Optional<Response>> {
    private final Request request;
    private final ClientConnector connector;
    private final ServerAccountRepository serverAccountRepository;
    private final PersonRepository personRepository;
    private final CollectionRepository collectionRepository;
    private final CommandExtractorService commandExtractorService;


    public RequestHandler(@NotNull Request request, ClientConnector connector, CollectionRepository collectionRepository, ServerAccountRepository serverAccountRepository, PersonRepository personRepository, CommandExtractorService commandExtractorService) {
        this.connector = connector;
        this.request = request;
        this.serverAccountRepository = serverAccountRepository;
        this.personRepository = personRepository;
        this.collectionRepository = collectionRepository;
        this.commandExtractorService = commandExtractorService;
    }


    @Override
    public Optional<Response> call() {
            if (request.getMessages().get(0).equals("commands") && connector.getClient().isFirstMessageFromClient()) {
                return Optional.of(connector.setCommands());
            } else {
                try {
                    ServerCommand commandToExecute = commandExtractorService.mapCommand(request.getCommandToExecute(), request.getMessages().get(0));
                    Response response = executeCommand(commandToExecute);
                    System.out.printf("команда %s выполнена%n", request.getCommandToExecute().getName());

                    try {
                        connector.getClient().checkAnswer(response, request);
                    } catch (NoAccountFounded e) {
                        response.setSuccess(false);
                        response.setMessages(Stream.of("Аккаунт не найден").collect(Collectors.toCollection(ArrayList::new)));
                    } catch (InvalidPassword e) {
                        response.setSuccess(false);
                        response.setMessages(Stream.of("Неверный пароль").collect(Collectors.toCollection(ArrayList::new)));
                    }
                    return Optional.of(response);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    System.out.println("Ошибка: неверно реализована команда");
                    e.printStackTrace();
                    return Optional.empty();
                }
            }


    }

    public Response executeCommand(ServerCommand command) {
        return command.execute(collectionRepository,serverAccountRepository, personRepository);
    }
}