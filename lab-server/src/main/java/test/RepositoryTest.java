package test;

import com.dar.server.SpringConfig;
import com.dar.server.data.dao.PerdonDao;
import com.dar.server.data.dao.ServerAccountDao;
import com.dar.server.data.entities.*;
import com.dar.server.data.repositories.ServerAccountRepository;
import com.dar.server.data.repositories.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RepositoryTest {
    ServerAccountRepository serverAccountRepository;

    PersonRepository personRepository;
    PerdonDao perdonDao;
    ServerAccountDao serverAccountDao;

    @Before
    public void init() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(SpringConfig.class);
        context.refresh();

        this.serverAccountDao = context.getBean(ServerAccountDao.class);
        this.perdonDao = context.getBean(PerdonDao.class);
        this.serverAccountRepository = context.getBean(ServerAccountRepository.class);


        this.personRepository = context.getBean(PersonRepository.class);
    }

    @Test
    @Transactional
    public void testSpaceMarineRepo() {

        PersonEntity spm = new PersonEntity("name", new CoordinatesEntity(1L, 33L), 23, 33f, Color.GREEN, new LocationEntity(1, null, 1));
        personRepository.add(spm);
        assert personRepository.get(spm.getId()).isPresent();
        personRepository.remove(spm);
        assert personRepository.get(spm.getId()).isEmpty();

    }

    @Test
    @Transactional
    public void testServerAccountRepo() {
        AccountEntity account = new AccountEntity("logint_test", "password_test");
        serverAccountRepository.add(account);
        assert serverAccountRepository.get(account.getId()).isPresent();
        serverAccountRepository.remove(account);
        assert serverAccountRepository.get(account.getId()).isEmpty();
        account = new AccountEntity("logint_test2", "password_test2");
        serverAccountRepository.add(account);
        assert serverAccountRepository.get(account.getLogin()).isPresent();
        serverAccountRepository.remove(account);
        assert serverAccountRepository.get(account.getLogin()).isEmpty();

    }

}
