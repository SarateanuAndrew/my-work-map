package com.example.myworkmap.service.implementation.intergration;

import com.example.myworkmap.model.dto.AdminsDto;
import com.example.myworkmap.model.dto.request.AdminPageRequestDto;
import com.example.myworkmap.model.dto.response.AdminsResponseDto;
import com.example.myworkmap.model.projections.AdminsDtoPImpl;
import com.example.myworkmap.repository.AdminsRepository;
import com.example.myworkmap.service.implementation.config.PgTestContainerInit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.ALL;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminsServiceImplIntegrationTest extends PgTestContainerInit {
    @Autowired
    private AdminsRepository adminsRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveAdmin() throws Exception {
        AdminsDto adminsDto = AdminsDto.builder()
                .level(2)
                .lastName("fd")
                .firstName("fsae")
                .email("fsf")
                .password("fdsa")
                .rolesId(1L)
                .build();

        mockMvc.perform(post("https://localhost:8080/map/admins/save-admin")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminsDto)))
                .andExpect(status().isCreated());
        assertThat(adminsRepository.findAllAdmins().size()).isEqualTo(2);
    }

    @Test
    void findAllAdmin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("https://localhost:8080/map/admins/find-all-admin")
                        .contentType(ALL))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<AdminsResponseDto> adminsResponseDtos = List.of(objectMapper.readValue(contentAsString, AdminsResponseDto[].class));
        System.out.println();
        assertThat(adminsResponseDtos).size().isEqualTo(1);
    }

    @Test
    void getAdminById() throws Exception {
        AdminsResponseDto adminsDto = objectMapper.readValue(mockMvc.perform(get("https://localhost:8080/map/admins/get-admin-by-id/{id}", 100L)
                        .contentType(ALL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), AdminsResponseDto.class);
        assertThat(adminsDto.getFirstName()).isEqualTo("f1");
        System.out.println();
    }

    @Test
    void deleteAdmin() throws Exception {
        assertThat(adminsRepository.findAll()).size().isEqualTo(1);
        mockMvc.perform(delete("https://localhost:8080/map/admins/delete-admin/{id}", 100L)
                        .contentType(ALL))
                .andExpect(status().isOk());
        assertThat(adminsRepository.findAll()).isEmpty();
    }

    @Test
    void getAdminsByRoleName() throws Exception {
        List<AdminsDto> roleName = List.of(objectMapper.readValue(mockMvc.perform(get("/map/admins/get-admin-by-role-name")
                        .contentType(APPLICATION_JSON)
                        .param("roleName", "MERCHANT"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), AdminsDto[].class));

        assertThat(roleName.size()).isEqualTo(1);
        assertThat(roleName.get(0).getLastName()).isEqualTo("l1");
    }

    @Test
    void getAdminsByRoleNameFetch() throws Exception {
        List<AdminsDto> roleName = List.of(objectMapper.readValue(mockMvc.perform(get("/map/admins/get-admin-by-role-name-fetch")
                        .contentType(APPLICATION_JSON)
                        .param("roleName", "MERCHANT"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), AdminsDto[].class));
        assertThat(roleName.size()).isEqualTo(1);
    }

    @Test
    void getAdminsWithLastPassCreation() throws Exception {
        List<AdminsDtoPImpl> adminsDtoP = List.of(objectMapper.readValue(mockMvc.perform(
                        get("/map/admins/admins-last-pass-creation")
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), AdminsDtoPImpl[].class));
        assertThat(adminsDtoP.get(0).getPasswordCreatedTime()).isEqualTo(LocalDateTime.of(2022, 6, 6, 11, 12, 0));
    }

    @Test
    void getAdminsWithLastPassCreationPagination() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                        get("/map/admins/admins-last-pass-creation-pagination")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(AdminPageRequestDto.builder().build())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();


        String contentAsString = response.getContentAsString();
        String expectedResult = """
                {
                    "content": [
                        {
                            "adminId": 100,
                            "firstName": "f1",
                            "lastName": "l1",
                            "email": "email1@gmail.com",
                            "getRoleName": "MERCHANT",
                            "passwordCreatedTime": "2022-06-06T11:12:00"
                        }
                    ],
                    "pageable": "INSTANCE",
                    "last": true,
                    "totalPages": 1,
                    "totalElements": 1,
                    "size": 1,
                    "number": 0,
                    "sort": {
                        "empty": true,
                        "unsorted": true,
                        "sorted": false
                    },
                    "first": true,
                    "numberOfElements": 1,
                    "empty": false
                }
                """;
        JSONAssert.assertEquals(contentAsString, expectedResult, JSONCompareMode.NON_EXTENSIBLE);
    }
}