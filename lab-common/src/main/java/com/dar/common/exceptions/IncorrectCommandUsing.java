package com.dar.common.exceptions;

public class IncorrectCommandUsing extends RuntimeException {
    public IncorrectCommandUsing() {
        super();
    }

    public IncorrectCommandUsing(String s) {
        super(s);
    }
}

