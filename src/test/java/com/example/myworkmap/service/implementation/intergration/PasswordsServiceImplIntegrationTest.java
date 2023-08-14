package com.example.myworkmap.service.implementation.intergration;

import com.example.myworkmap.model.dto.PasswordsDto;
import com.example.myworkmap.repository.PasswordsRepository;
import com.example.myworkmap.service.implementation.config.PgTestContainerInit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PasswordsServiceImplIntegrationTest extends PgTestContainerInit {
    @Autowired
    private PasswordsRepository passwordsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void savePassword() throws Exception {
        PasswordsDto passwordsDto = PasswordsDto.builder()
                .passwordHash("fdfsdf")
                .adminsId(100L)
                .build();
        mockMvc.perform(post("https://localhost:8080/map/passwords/save-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordsDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deletePassword() throws Exception {
        mockMvc.perform(delete("https://localhost:8080/map/passwords/delete-password/{id}", 200L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertThat(passwordsRepository.findAllPasswords().size()).isEqualTo(1);
    }

    @Test
    void getAllPasswords() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("https://localhost:8080/map/passwords/find-all-password")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<PasswordsDto> passwordsDtos = Arrays.asList(objectMapper.readValue(contentAsString, PasswordsDto[].class));
        assertThat(passwordsDtos).size().isEqualTo(2);
    }

    @Test
    void getPasswordById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("https://localhost:8080/map/passwords/get-password-by-id/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        PasswordsDto passwordsDto = objectMapper.readValue(contentAsString, PasswordsDto.class);
        assertThat(passwordsDto.getPasswordHash()).isEqualTo("hash1");
    }
}