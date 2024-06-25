package com.dar.server.main;


import com.dar.common.data.AccountDTO;
import com.dar.server.SpringConfig;
import com.dar.server.data.entities.AccountEntity;
import com.dar.server.data.repositories.ServerAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * main-класс,принимающий подключения и задающий конфигурации
 */
public class App {
    public static final Logger log = LogManager.getLogger(App.class.getName());


    private App() {
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringConfig.class);
        context.refresh();

        AccountEntity acc = new AccountEntity(AccountDTO.getCommonAcc().getLogin(), AccountDTO.getCommonAcc().getPassword());
        AccountEntity.commonAcc = acc;
        context.getBean(ServerAccountRepository.class).add(acc);




        context.getBean("server", Server.class).run();
    }




}