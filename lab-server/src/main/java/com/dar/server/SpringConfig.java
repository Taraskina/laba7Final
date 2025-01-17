package com.dar.server;

import com.dar.server.commands.*;
import com.dar.server.data.entities.PersonEntity;
import com.dar.server.util.ServerCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Stream;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan("com.dar.server")
public class SpringConfig {
    @Bean
    ServerSocketChannel serverSocketChannel() throws IOException {
        return ServerSocketChannel.open();
    }

    @Bean
    Selector selector() throws IOException {
        return Selector.open();
    }

    @Bean
    PriorityQueue<PersonEntity> spaceMarineEntityPriorityQueue() {
        return new PriorityQueue<>();
    }

    @Bean
    EntityManager entityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(System.getenv("PERSIST"));
        return emf.createEntityManager();
    }

    @Bean
    HashMap<String, Function<String, ServerCommand>> hashMap() {
        HashMap<String, Function<String, ServerCommand>> map = new HashMap<>();
        Stream.of(Add.class, Help.class, Clear.class, Enter.class, Exit.class, Info.class, Register.class, RemoveById.class, Save.class, Show.class, UpdateById.class).
                forEach(w -> {
                    try {
                        map.put(String.valueOf(w.getField("name").get(w.getConstructor().newInstance())), args -> {
                            try {
                                return (ServerCommand) w.getMethod("staticFactory", String[].class, String.class).invoke(null, args, null);
                            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    } catch (IllegalAccessException | NoSuchFieldException | InvocationTargetException |
                             InstantiationException | NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
        return map;
    }
}
