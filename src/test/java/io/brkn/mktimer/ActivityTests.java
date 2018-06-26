package io.brkn.mktimer;

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
    public void getActivitiesWithoutAuthorizationShouldFailTest() throws Exception {
        mvc.perform(get("/activity")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getActivitiesWithBasicAuthShouldFailTest() throws Exception {
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":"
                + password).getBytes("UTF-8"));

        mvc.perform(get("/activity")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getActivitiesWithJwtAuthShouldWorkTest() throws Exception {
        mvc.perform(get("/activity")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk());
    }

    @Test
    public void getActivitesWithWrongJwtAuthShouldFailTest() throws Exception {
        mvc.perform(get("/activity")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader + "someweirdtext"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void startActivityUnauthorizedShouldFailTest() throws Exception {
        createCategory(testCategoryName);

        mvc.perform(post("/activity/start?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isForbidden());
    }

    @Test
    public void startActivityWithJwtAuthShouldWorkTest() throws Exception {
        createCategory(testCategoryName);

        mvc.perform(post("/activity/start?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk());
    }

    @Test
    public void startActivityWithWrongJwtAuthShouldFailTest() throws Exception {
        createCategory(testCategoryName);

        mvc.perform(post("/activity/start?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader + "sometext"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void startActivityWithBasicAuthShouldWorkTest() throws Exception {
        createCategory(testCategoryName);
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":"
                + password).getBytes("UTF-8"));

        mvc.perform(post("/activity/start?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isOk());
    }

    @Test
    public void startActivityWithBasicAuthWrongPasswordShouldFailTest() throws Exception {
        createCategory(testCategoryName);
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":"
                + password + "somecharacters").getBytes("UTF-8"));

        mvc.perform(post("/activity/start?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void stopActivityUnauthorizedShouldFailTest() throws Exception {
        createCategory(testCategoryName);

        mvc.perform(post("/activity/stop?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isForbidden());
    }

    @Test
    public void stopActivityJwtAuthShouldWorkTest() throws Exception {
        createCategory(testCategoryName);

        mvc.perform(post("/activity/stop?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk());
    }

    @Test
    public void stopActivityWithWrongJwtAuthShouldFailTest() throws Exception {
        createCategory(testCategoryName);

        mvc.perform(post("/activity/stop?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader + "something"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void stopActivityBasicAuthShouldWorkTest() throws Exception {
        createCategory(testCategoryName);
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":"
                + password).getBytes("UTF-8"));

        mvc.perform(post("/activity/stop?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isOk());
    }

    @Test
    public void stopActivityBasicAuthWrongPasswordShouldFailTest() throws Exception {
        createCategory(testCategoryName);
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":"
                + password + "somecharacters").getBytes("UTF-8"));

        mvc.perform(post("/activity/stop?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isUnauthorized());
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
