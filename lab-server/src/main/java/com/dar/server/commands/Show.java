package com.dar.server.commands;

import com.dar.common.data.Executable;
import com.dar.common.data.Response;
import com.dar.common.utilites.CommandType;
import com.dar.server.util.ServerCommand;

public class Show extends ServerCommand implements Executable {
    public final String name = "show";

    public Show() {
        super();
        this.setCommandType(CommandType.WITHOUT_ARGUMENTS);
    }

    public static Show staticFactory(String[] args, String value) {
        Show inst = new Show();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;

    public Response calling() {
        Response resp = super.calling();

        if (getCollectionRepository().getCollectionSize() == 0) {
            resp.addMessage("В коллекции нет элементов");
        }

        getCollectionRepository().getCollectionStream().forEach(
                w -> resp.addMessage(w + "\n"));
        return resp;
    }
}
