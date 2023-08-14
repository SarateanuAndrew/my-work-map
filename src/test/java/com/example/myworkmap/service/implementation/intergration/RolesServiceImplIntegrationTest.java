package com.example.myworkmap.service.implementation.intergration;

import com.example.myworkmap.model.dto.RolesDto;
import com.example.myworkmap.repository.RolesRepository;
import com.example.myworkmap.service.RolesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RolesServiceImplIntegrationTest extends PasswordsServiceImplIntegrationTest {
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private RolesService rolesService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void saveRole() throws Exception {
        RolesDto rolesDto = RolesDto.builder()
                .name("")
                .description("Works")
                .build();
        mockMvc.perform(post("https://localhost:8080/map/roles/save-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rolesDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteRole() throws Exception {
        mockMvc.perform(delete("https://localhost:8080/map/roles/delete-role/{id}", 100L)
                        .contentType(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    void getAllRoles() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("https://localhost:8080/map/roles/find-all-role")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<RolesDto> rolesDtos = Arrays.asList(objectMapper.readValue(contentAsString, RolesDto[].class));
        assertThat(rolesDtos.size()).isEqualTo(4);
    }

    @Test
    void getRoleById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("https://localhost:8080/map/roles/get-role-by-id/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        RolesDto rolesDto = objectMapper.readValue(contentAsString, RolesDto.class);
        System.out.println();
        assertThat(rolesDto.getName()).isEqualTo("SUPERVISOR");
    }

    @Test
    void updateRole() throws Exception {
        RolesDto rolesDto = RolesDto.builder()
                .id(1L)
                .name("Worker")
                .description("Works")
                .build();
        mockMvc.perform(put("http://localhost:8080/map/roles/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rolesDto)))
                .andExpect(status().isOk());
        assertThat(rolesRepository.findById(1L).orElseThrow().getName()).isEqualTo(rolesDto.getName());
    }

    @Test
    void updateRoleQuery() throws Exception {
        RolesDto rolesDto = RolesDto.builder()
                .id(1L)
                .name("Worker")
                .description("Works")
                .build();
        mockMvc.perform(put("http://localhost:8080/map/roles/update-with-query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rolesDto)))
                .andExpect(status().isOk());
        assertThat(rolesRepository.findById(1L).orElseThrow().getName()).isEqualTo(rolesDto.getName());
    }

    @Test
    void groupDaysOfBirthday(){
        Map<DayOfWeek, List<Integer>> dayOfWeekLongMap = rolesService.groupDaysOfBirthday(LocalDate.of(2002, 6, 15));
        System.out.println();
    }
}