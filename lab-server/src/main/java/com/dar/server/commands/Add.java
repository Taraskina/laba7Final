package com.dar.server.commands;

import com.dar.common.data.Executable;
import com.dar.common.data.Response;
import com.dar.common.utilites.CommandType;
import com.dar.server.data.entities.PersonEntity;
import com.dar.server.util.ServerCommand;

public class Add extends ServerCommand implements Executable {
    public final String name = "add";

    public Add() {
        super();
        this.setCommandType(CommandType.ELEMENT_ARGUMENTED);
    }

    public static Add staticFactory(String[] args, String value) {
        Add inst = new Add();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    public Response calling( PersonEntity spm) {
        Response resp = super.calling();
        spm.setOwner(getUser());
        getCollectionRepository().add(spm);
        getSpaceMarineRepository().add(spm);
        return resp;

    }

    @Override
    public Response calling() {
        PersonEntity spm = PersonEntity.newInstance(getArgs());
        return this.calling( spm);
    }
}
