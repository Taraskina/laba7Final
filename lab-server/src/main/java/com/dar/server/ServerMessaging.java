package com.dar.server;


import com.dar.common.data.Request;
import com.dar.common.data.Response;
import com.dar.common.exceptions.MessageWasNotReadedSuccessfull;
import com.dar.common.utilites.JsonSerializer;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public final class ServerMessaging {
    private ServerMessaging() {
    }

    public static Request readRequest(SocketChannel clientChannel) throws IOException, MessageWasNotReadedSuccessfull {
        ByteBuffer buf = ByteBuffer.allocate(clientChannel.socket().getReceiveBufferSize());
        int readed = clientChannel.read(buf);
        if (readed > 0) {
            buf.flip();
            String msg = new String(buf.array());
            return JsonSerializer.deserialize(msg, new TypeReference<>() {
            });
        } else {
            throw new MessageWasNotReadedSuccessfull();
        }
    }

    public static void sendResponse(SocketChannel clientChannel, String message) throws IOException {
        Response resp = new Response();
        resp.addMessage(message);
        String json = JsonSerializer.toJson(resp);
        ByteBuffer buf = ByteBuffer.allocate(json.getBytes().length).put(json.getBytes());
        buf = buf.flip();
        while (buf.hasRemaining()) {
            clientChannel.write(buf);
        }
    }

    public static void sendResponse(SocketChannel clientChannel, Response resp) throws IOException {
        //resp.setUser(new Account(resp.getUser().getLogin(), resp.getUser().getPassword()));
        String message = JsonSerializer.toJson(resp);
        ByteBuffer buf = ByteBuffer.allocate(message.getBytes().length);
        buf.put(message.getBytes());
        buf = buf.flip();
        while (buf.hasRemaining()) {
            clientChannel.write(buf);
        }
    }
}
