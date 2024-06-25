package com.dar.server.commands;


import com.dar.common.data.Executable;
import com.dar.common.data.Response;
import com.dar.common.utilites.CommandType;
import com.dar.server.data.entities.PersonEntity;
import com.dar.server.util.ServerCommand;

public class UpdateById extends ServerCommand implements Executable {
    public final String name = "update_by_id";

    public UpdateById() {
        super();
        this.setCommandType(CommandType.ELEMENT_AND_VALUE_ARGUMENTED);
    }

    public static UpdateById staticFactory(String[] args, String value) {
        UpdateById inst = new UpdateById();
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
                    PersonEntity spm = getCollectionRepository().getCollectionStream().filter(x -> x.getId() == Integer.parseInt(this.getValue())).findFirst().get();
                    spm.update(this.getArgs());
                    getSpaceMarineRepository().update(spm);
                    resp.addMessage("Объект успешно обновлен");
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
