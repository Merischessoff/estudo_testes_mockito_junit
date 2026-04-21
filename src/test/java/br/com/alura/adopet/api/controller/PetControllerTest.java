package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mvc; //faz com que faça uma requisição fake

    @MockBean //service fake não toca no banco
    private PetService petService;

    @Test
    void listarTodosDisponiveis() throws Exception {
        // ARRANGE
        // Aqui diz que o service retorna uma lista vazia, para quando der o get não dar null
        when(petService.buscarPetsDisponiveis()).thenReturn(new ArrayList<>());

        // ACT
        var response = mvc.perform(
            get("/pets")
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
}