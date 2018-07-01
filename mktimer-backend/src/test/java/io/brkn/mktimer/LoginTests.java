package io.brkn.mktimer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.brkn.mktimer.web.forms.LoginForm;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static junit.framework.TestCase.assertNotNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginTests extends BaseIntegrationTest {

    protected String authHeader;

    @Test
    public void jwtLogInShouldReturnAuthTokenHeaderTest() throws Exception {
        LoginForm loginForm = new LoginForm(username, password);

        MvcResult result = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(loginForm)))
                .andExpect(status().isOk())
                .andReturn();

        authHeader = result.getResponse().getHeader(AUTHORIZATION);

        assertNotNull(authHeader);
    }

}
