package io.r4v3chain.backend.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r4v3chain.backend.config.ApiRoutes;
import io.r4v3chain.backend.support.MockMvcIntegrationSupport;
import io.r4v3chain.backend.support.MockMvcIntegrationSupport.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void register_returnsTokenAndProfile() throws Exception {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        String username = "reg" + suffix;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "%s",
                                  "email": "%s@r4v3chain.dev",
                                  "password": "password123"
                                }
                                """.formatted(username, username)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.user.username").value(username))
                .andExpect(jsonPath("$.user.email").value(username + "@r4v3chain.dev"));
    }

    @Test
    void login_returnsTokenForExistingUser() throws Exception {
        Session session = MockMvcIntegrationSupport.register(mockMvc);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "identifier": "%s",
                                  "password": "password123"
                                }
                                """.formatted(session.username())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.user.username").value(session.username()));
    }

    @Test
    void me_withoutAuth_isUnauthorized() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void me_withAuth_returnsProfile() throws Exception {
        Session session = MockMvcIntegrationSupport.register(mockMvc);

        mockMvc.perform(get("/api/auth/me").header("Authorization", session.authHeader()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(session.username()))
                .andExpect(jsonPath("$.id").value(session.userId()));
    }

    @Test
    void logout_revokesRefreshToken() throws Exception {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        String username = "logout" + suffix;

        String registerBody = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "%s",
                                  "email": "%s@r4v3chain.dev",
                                  "password": "password123"
                                }
                                """.formatted(username, username)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode tokens = objectMapper.readTree(registerBody);
        String accessToken = tokens.path("accessToken").asText();
        String refreshToken = tokens.path("refreshToken").asText();

        mockMvc.perform(post("/api/auth/logout")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"refreshToken": "%s"}
                                """.formatted(refreshToken)))
                .andExpect(status().isNoContent());

        mockMvc.perform(post(ApiRoutes.AUTH_REFRESH_V1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"refreshToken": "%s"}
                                """.formatted(refreshToken)))
                .andExpect(status().isUnauthorized());
    }
}
