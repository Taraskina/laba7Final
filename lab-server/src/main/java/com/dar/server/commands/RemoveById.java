package com.dar.server.commands;


import com.dar.common.data.Executable;
import com.dar.common.data.Response;
import com.dar.common.utilites.CommandType;
import com.dar.server.util.ServerCommand;

public class RemoveById extends ServerCommand implements Executable {
    public final String name = "remove_by_id";

    public RemoveById() {
        super();
        this.setCommandType(CommandType.VALUE_ARGUMENTED);
    }

    public static RemoveById staticFactory(String[] args, String value) {
        RemoveById inst = new RemoveById();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    ;


    public Response calling() {
        Response resp = super.calling();
        if (getCollectionRepository().getCollectionStream().anyMatch(w -> String.valueOf(w.getId()).equals(this.getValue()))) {
            getCollectionRepository().getCollectionStream().filter(w -> String.valueOf(w.getId()).equals(this.getValue())).forEach(w -> {
                if (w.getOwner().equals(getUser())) {
                    getCollectionRepository().remove(w);
                    getSpaceMarineRepository().remove(w);
                    resp.addMessage("Объект успешно удален");
                } else {
                    resp.addMessage("Объект с этим id принадлежит не вам");
                    resp.setSuccess(false);
                }
            });

        } else {
            resp.addMessage("Ошибка, не существует элемента с таким ID");
            resp.setSuccess(false);
        }
        return resp;
    }

}
