package com.dar.client.commands.types;

import com.dar.client.main.Context;

import static com.dar.common.utilites.CheckingReader.readSomeArgs;

/**
 * Класс команд,имеющих аргументами  String[] - аргументы для управления сущностями
 */
public class ElementArgumented extends ElementAndValueArgumented {
    public ElementArgumented(String[] args) {
        super(null, args);
    }

    public ElementArgumented(Context ctx) {
        super(null, readSomeArgs(9, "s,l,l,l,f,s,s,s".split(","), ctx.getScanner(), (
                        "Введите имя;" +
                                "Введите целочисленную x-координату (x<=625));" +
                                "Введите y-координату в формате деcятичной дроби (y>=-354.0);" +
                                "Введите вес;" +
                                "Введите десятичное число,характеризующее рост;" +
                                """
                                        Введите один из цветов:
                                            RED,
                                            GREEN,
                                            BLUE,
                                            YELLOW;""" +
                                "Введите название главы;" +
                                "Введите название главы;").split(";"),
                "more length 0;less than 626;more than -353.0;more than 0;;;is color;more length 0;more length 0".split(";")));
    }
}

