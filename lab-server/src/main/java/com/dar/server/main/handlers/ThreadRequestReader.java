package com.dar.server.main.handlers;

import com.dar.common.data.Request;
import com.dar.common.exceptions.MessageWasNotReadedSuccessfull;
import com.dar.server.main.ClientConnector;
import com.dar.server.util.ServerMessaging;

import java.io.IOException;

import static com.dar.common.utilites.JsonSerializer.toJson;
import static com.dar.server.main.App.log;

@Deprecated
public class ThreadRequestReader implements Runnable{
    private final ClientConnector client;


    public ThreadRequestReader(ClientConnector client) {
        this.client = client;
    }
    @Override
    public void run() {
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
            //Optional<Response> optionalRequest =new RequestHandler(request, client).call();
            //optionalRequest.ifPresent(client.responses::add);
        }
    }
}
