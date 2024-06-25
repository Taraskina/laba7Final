package com.dar.client.main;

import com.dar.common.exceptions.MessageWasNotReadedSuccessfull;
import com.dar.common.data.Response;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.NoSuchElementException;

import static com.dar.client.main.Main.socketChannel;

/**
 * Поток, считывающий ответы сервера и выводящий их
 */
public class ReadingThread extends Thread {
    private final Selector selector;

    public ReadingThread(Selector selector) {
        this.selector = selector;
    }

    /**
     * метод считывания ответа сервера
     *
     * @throws MessageWasNotReadedSuccessfull
     */
    public static void getAnswerFromServer() throws MessageWasNotReadedSuccessfull {
        Response response = null;
        try {
            response = ClientMessaging.readResponse(socketChannel);
        } catch (IOException ignored) {
        }
        if (response != null) {
            if (!response.getMessages().isEmpty()) {
                response.getMessages().forEach(System.out::println);
                Main.flag = response.isFlag();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                try {
                    if (selector.selectNow() >= 0) {
                        for (SelectionKey key : selector.selectedKeys()) {
                            getAnswerFromServer();
                            socketChannel.register(selector, SelectionKey.OP_READ + SelectionKey.OP_WRITE);
                        }
                    }
                } catch (MessageWasNotReadedSuccessfull ignored) {
                }
            } catch (NoSuchElementException e) {
                System.err.println("Не надо вводить ctrl+D !!!");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
