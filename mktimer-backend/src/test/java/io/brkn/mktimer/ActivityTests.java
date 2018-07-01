package io.brkn.mktimer;

import io.brkn.mktimer.domain.Activity;
import io.brkn.mktimer.web.forms.CreateActivityForm;
import io.brkn.mktimer.web.forms.CreateCategoryForm;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    public void getActivitiesFromCategoryShouldOnlyReturnForThatCategoryTest() throws Exception {
        String secondTestCategory = testCategoryName + "2";

        createCategory(testCategoryName);
        createCategory(secondTestCategory);

        startActivity(testCategoryName);
        startActivity(secondTestCategory);

        stopActivity(testCategoryName);
        stopActivity(secondTestCategory);

        mvc.perform(get("/activity?category=" + testCategoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getActivitiesBeforeDateTimeShouldWorkTest() throws Exception {
        createCategory(testCategoryName);
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());

        createActivity(new CreateActivityForm(now.minusMinutes(60), now.minusMinutes(30), testCategoryName));
        createActivity(new CreateActivityForm(now.plusMinutes(30), now.plusMinutes(60), testCategoryName));

        mvc.perform(get("/activity?before=" + now.toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getActivitiesAfterDateTimeShouldWorkTest() throws Exception {
        createCategory(testCategoryName);
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());

        createActivity(new CreateActivityForm(now.minusMinutes(60), now.minusMinutes(30), testCategoryName));
        createActivity(new CreateActivityForm(now.plusMinutes(30), now.plusMinutes(60), testCategoryName));

        mvc.perform(get("/activity?after=" + now.toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getActivitiesBeforeAndAfterDateTimeShouldWorkTest() throws Exception {
        createCategory(testCategoryName);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());

        createActivity(new CreateActivityForm(now.minusMinutes(60), now.minusMinutes(30), testCategoryName));
        createActivity(new CreateActivityForm(now.plusMinutes(15), now.plusMinutes(20), testCategoryName));
        createActivity(new CreateActivityForm(now.plusMinutes(30), now.plusMinutes(60), testCategoryName));

        mvc.perform(get("/activity?before=" + now.plusMinutes(20).toString() + "&after=" + now.toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void createActivityWithOnlyStartTimeTest() throws Exception {
        createCategory(testCategoryName);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        CreateActivityForm createActivityForm = new CreateActivityForm(now, testCategoryName);

        MvcResult result = mvc.perform(post("/activity")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(createActivityForm))
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk())
                .andReturn();

        Activity createdActivity = objectMapper.readValue(result.getResponse().getContentAsString(), Activity.class);

        assertTrue(createdActivity.getStartDateTime().isEqual(now));
        assertNull(createdActivity.getEndDateTime());
    }

    @Test
    public void createActivityWithStartAndEndTest() throws Exception {

    }

    private void createCategory(String categoryName) throws Exception {
        CreateCategoryForm createCategoryForm = new CreateCategoryForm(categoryName);

        mvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(createCategoryForm))
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk());
    }

    private MvcResult startActivity(String categoryName) throws Exception {
        return mvc.perform(post("/activity/start?category=" + categoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk())
                .andReturn();
    }

    private MvcResult stopActivity(String categoryName) throws Exception {
        return mvc.perform(post("/activity/stop?category=" + categoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk())
                .andReturn();
    }

    private void createActivity(CreateActivityForm createActivityForm) throws Exception {
        mvc.perform(post("/activity")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(createActivityForm))
                .header(AUTHORIZATION, authHeader))
                .andExpect(status().isOk());
    }

}
