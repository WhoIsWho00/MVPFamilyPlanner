package com.example.familyplanner.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MainControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MainController mainController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(mainController)
                .build();
    }

    @Test
    void userAccess_ShouldReturnUserName_WhenAuthenticated() throws Exception {

        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("test@example.com");


        mockMvc.perform(get("/secured/user")
                        .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("test@example.com"));
    }

    @Test
    void userAccess_ShouldReturnNull_WhenNotAuthenticated() throws Exception {

        mockMvc.perform(get("/secured/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}