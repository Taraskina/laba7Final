package com.dar.server.commands;


import com.dar.common.data.Response;
import com.dar.common.utilites.CommandType;
import com.dar.server.util.ServerCommand;

@Deprecated

public class Save extends ServerCommand {
    public final String name = "save";

    public Save() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }

    public static Save staticFactory(String[] args, String value) {
        Save inst = new Save();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;


    public Response calling() {
        return super.calling();
    }

}