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

public class CategoryTests extends JwtLoggedInBaseTest {
    private String testCategory = "test_category_æøå";

    @Test
    public void getCategoriesWithoutAuthShouldFailTest() throws Exception {
        mvc.perform(get("/category")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getCategoriesWithJwtAuthShouldWorkTest() throws Exception {
        mvc.perform(get("/category")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk());
    }

    @Test
    public void getCategoriesWithWrongJwtAuthShouldFailTest() throws Exception {
        mvc.perform(get("/category")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader + "something"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getCategoriesWithBasicAuthShouldFailTest() throws Exception {
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":"
                + password).getBytes("UTF-8"));

        mvc.perform(get("/category")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createCategoryUnauthorizedShouldFailTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateCategoryForm createCategoryForm = new CreateCategoryForm(testCategory);

        mvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(createCategoryForm)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createCategoryWithBasicAuthShouldFailTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateCategoryForm createCategoryForm = new CreateCategoryForm(testCategory);
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":"
                + password).getBytes("UTF-8"));

        mvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(createCategoryForm))
                .header(AUTHORIZATION, basicAuthHeader))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createCategoryWithJwtAuthShouldWorkTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateCategoryForm createCategoryForm = new CreateCategoryForm(testCategory);

        mvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(createCategoryForm))
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk());
    }

    @Test
    public void createCategoryWithWrongJwtAuthShouldFailTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateCategoryForm createCategoryForm = new CreateCategoryForm(testCategory);

        mvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(createCategoryForm))
                .header(AUTHORIZATION, authHeader + "something"))
                .andExpect(status().isUnauthorized());
    }

}
