package io.brkn.mktimer;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MktimerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:testing.properties")
public abstract class BaseIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Value("${mktimer.username}")
    protected String username;

    @Value("${mktimer.password}")
    protected String password;

    @Autowired
    protected MockMvc mvc;

}
