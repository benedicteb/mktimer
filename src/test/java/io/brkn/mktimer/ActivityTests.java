package io.brkn.mktimer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.brkn.mktimer.web.forms.CreateCategoryForm;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ActivityTests extends JwtLoggedInBaseTest {
    private String testCategoryName = "test_category_æøå";

    @Test
    public void getActivitiesWithoutAuthorizationTest() throws Exception {
        mvc.perform(get("/activity")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getActivitiesWithBasicAuthTest() throws Exception {
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":"
                + password).getBytes("UTF-8"));

        mvc.perform(get("/activity")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getActivitiesWithJwtAuthTest() throws Exception {
        mvc.perform(get("/activity")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk());
    }

    @Test
    public void startActivityUnauthorizedTest() throws Exception {
        createCategory(testCategoryName);

        mvc.perform(post("/activity/start?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void startActivityJwtAuthTest() throws Exception {
        createCategory(testCategoryName);

        mvc.perform(post("/activity/start?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk());
    }

    @Test
    public void startActivityBasicAuthTest() throws Exception {
        createCategory(testCategoryName);
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":"
                + password).getBytes("UTF-8"));

        mvc.perform(post("/activity/start?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isOk());
    }

    private void createCategory(String categoryName) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateCategoryForm createCategoryForm = new CreateCategoryForm(categoryName);

        mvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(createCategoryForm))
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk());
    }

}
