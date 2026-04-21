package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.AdocaoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc; //faz com que faça uma requisição fake

    @MockBean //service fake não toca no banco
    private AbrigoService abrigoService;

    @MockBean //service fake não toca no banco
    private PetService petService;


    //@Autowired
    //private TestRestTemplate restTemplate; //faz requisição de verdade

    @Test
    void deveriaDevolverCodigo200ParaCadastrarPetSemErros() throws Exception {
        //ARRANGE
        String json = """
            {
                "tipo": "CACHORRO",
                "nome": "Antonia",
                "raca": "SRD",
                "idade": 6,
                "cor": "Amarelo",
                "peso": 7.0
            }
            """;

        //ACT
        var response = mvc.perform(
            post("/abrigos/{idOuNome}/pets", 1)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        /*ResponseEntity<Void> response = restTemplate.exchange(
            "/adocoes",
            HttpMethod.POST,
            new HttpEntity<>(json, headers),
            Void.class
        );*/

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void deveriaDevolverCodigo400ParaCadastrarPetComErros() throws Exception {
        //ARRANGE
        String json = """
            {
            }
            """;

        //ACT
        var response = mvc.perform(
            post("/abrigos/{idOuNome}/pets", 1)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        /*ResponseEntity<Void> response = restTemplate.exchange(
            "/adocoes",
            HttpMethod.POST,
            new HttpEntity<>(json, headers),
            Void.class
        );*/

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());

    }


    @Test
    void deveriaDevolverCodigo200ParaListarPetsDoAbrigo() throws Exception {
        // ARRANGE
        String idOuNome = "1";
        // Aqui diz que o service retorna uma lista vazia, para quando der o get não dar null
        when(abrigoService.listarPetsDoAbrigo(anyString())).thenReturn(new ArrayList<>());

        // ACT
        var response = mvc.perform(
            get("/abrigos/{idOuNome}/pets", idOuNome)
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaListarPetsDoAbrigo() throws Exception {
        // ARRANGE
        String idOuNome = "";
        // Aqui diz que o service retorna uma lista vazia, para quando der o get não dar null
        when(abrigoService.listarPetsDoAbrigo(anyString())).thenReturn(new ArrayList<>());

        // ACT
        var response = mvc.perform(
            get("/abrigos/{idOuNome}/pets", idOuNome)
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(404, response.getStatus());
    }

}