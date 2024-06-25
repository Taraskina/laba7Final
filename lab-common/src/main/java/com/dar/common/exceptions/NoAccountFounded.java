package com.dar.common.exceptions;

public class NoAccountFounded extends Exception{
    public NoAccountFounded() {
    }

    public NoAccountFounded(String message) {
        super(message);
    }
}
