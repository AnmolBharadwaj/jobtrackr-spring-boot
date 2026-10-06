package com.anmol.jobtrackr.controller;

import com.anmol.jobtrackr.service.JobApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobApplicationController.class)
@Import(com.anmol.jobtrackr.exception.GlobalExceptionHandler.class)
class JobApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobApplicationService service;

    @Test
    void createRejectsInvalidPayload() throws Exception {
        String invalidJson = """
                {
                  "companyName": "",
                  "role": "",
                  "status": null,
                  "appliedDate": "2030-01-01"
                }
                """;

        mockMvc.perform(post("/api/applications")
                        .contentType("application/json")
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}