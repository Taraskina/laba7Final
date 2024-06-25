package com.dar.server.main.handlers;

import com.dar.common.data.Request;
import com.dar.common.exceptions.MessageWasNotReadedSuccessfull;
import com.dar.server.main.ClientConnector;
import com.dar.server.util.ServerMessaging;

import java.io.IOException;
import java.util.concurrent.RecursiveAction;

import static com.dar.common.utilites.JsonSerializer.toJson;
import static com.dar.server.main.App.log;

/**
 * класс,считывающий запросы c помощью Executor-а
 */
public class RequestReader extends RecursiveAction {
    private final ClientConnector client;


    public RequestReader(ClientConnector client) {
        this.client = client;
    }

    @Override
    protected void compute() {
        Request request = null;
        try {
            request = ServerMessaging.readRequest(client.getChannel());
        } catch (IOException e) {
            log.error("непрочитали", e);
            e.printStackTrace();
        } catch (MessageWasNotReadedSuccessfull e) {
        }
        if (request != null) {
            System.out.println("прочитали: " + toJson(request));
            client.requestsToHandle.add(request);
        }
    }
}
