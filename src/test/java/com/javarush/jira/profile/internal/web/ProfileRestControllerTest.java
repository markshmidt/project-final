package com.javarush.jira.profile.internal.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static com.javarush.jira.login.internal.web.UserTestData.USER_MAIL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProfileRestControllerTest extends AbstractControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    private static final String REST_URL = ProfileRestController.REST_URL;

    @Autowired

    private MockMvc mockMvc;

    @Test
    @WithUserDetails(USER_MAIL)
    void getProfile_success() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void getProfile_unauthorized() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void updateProfile_success() throws Exception {
        ProfileTo updated = new ProfileTo(1L,
                Set.of("DAILY_DIGEST", "TASK_REMINDER"),
                Set.of(new ContactTo("tg", "@user")));

        mockMvc.perform(put(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void updateProfile_invalid() throws Exception {
        ProfileTo invalid = new ProfileTo(1L, null, null); // нарушает @NotNull

        mockMvc.perform(put(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithAnonymousUser
    void updateProfile_unauthorized() throws Exception {
        ProfileTo updated = new ProfileTo(1L,
                Set.of("TASK_REMINDER"),
                Set.of(new ContactTo("tg", "@anon")));

        mockMvc.perform(put(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isUnauthorized());
    }
}
