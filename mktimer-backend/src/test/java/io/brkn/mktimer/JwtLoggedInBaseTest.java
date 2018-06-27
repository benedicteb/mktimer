package io.brkn.mktimer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.brkn.mktimer.web.forms.LoginForm;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MktimerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:testing.properties")
public abstract class JwtLoggedInBaseTest extends BaseIntegrationTest {

    protected String authHeader;

    @Before
    public void jwtLogIn() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginForm loginForm = new LoginForm(username, password);

        MvcResult result = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(loginForm)))
                .andExpect(status().isOk())
                .andReturn();

        authHeader = result.getResponse().getHeader(AUTHORIZATION);
    }

}
