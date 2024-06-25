package com.dar.server.main.services;

import com.dar.common.data.Request;
import com.dar.common.data.Response;
import com.dar.server.data.repositories.CollectionRepository;
import com.dar.server.data.repositories.ServerAccountRepository;
import com.dar.server.data.repositories.PersonRepository;
import com.dar.server.main.ClientConnector;
import com.dar.server.main.handlers.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestHandlerService {
    private final CollectionRepository collectionRepository;
    private final ServerAccountRepository serverAccountRepository;
    private final PersonRepository spaceMarineRepository;
    private final CommandExtractorService commandExtractorService;

    @Autowired
    public RequestHandlerService(CollectionRepository collectionRepository, ServerAccountRepository serverAccountRepository, PersonRepository spaceMarineRepository, CommandExtractorService commandExtractorService) {
        this.collectionRepository = collectionRepository;
        this.serverAccountRepository = serverAccountRepository;
        this.spaceMarineRepository = spaceMarineRepository;
        this.commandExtractorService = commandExtractorService;
    }

    public Optional<Response> handleRequest(Request request, ClientConnector connector) {
        RequestHandler requestHandler = new RequestHandler(request, connector, collectionRepository, serverAccountRepository, spaceMarineRepository, commandExtractorService);
        return requestHandler.call();
    }

}
