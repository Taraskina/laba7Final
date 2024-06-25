package com.dar.client.exceptions;

public class NotFoundedCommand extends RuntimeException {
    public NotFoundedCommand() {
        super();
    }

    public NotFoundedCommand(String msg) {
        super(msg);
    }
}
