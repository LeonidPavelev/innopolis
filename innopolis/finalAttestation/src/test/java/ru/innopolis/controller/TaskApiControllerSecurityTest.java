package ru.innopolis.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskApiControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllTasks_ShouldReturnUnauthorized_ForAnonymousUser() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void grantAdminRole_ShouldReturnOk_ForAdmin() throws Exception {
        mockMvc.perform(post("/api/admin/users/1/grant-admin"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void grantAdminRole_ShouldReturnForbidden_ForNonAdmin() throws Exception {
        mockMvc.perform(post("/api/admin/users/1/grant-admin"))
                .andExpect(status().isForbidden());
    }
}
