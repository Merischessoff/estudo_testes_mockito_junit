package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AdocaoControllerTest {

    @Autowired
    private MockMvc mvc; //faz com que faça uma requisição fake

    @MockBean //service fake não toca no banco
    private AdocaoService adocaoService;


    //@Autowired
    //private TestRestTemplate restTemplate; //faz requisição de verdade

    @Test
    void deveriaDevolverCodigo200ParaSolicitarAdocaoSemErros() throws Exception {
        //ARRANGE
        String json = """
        {
        "idPet":1,
        "idTutor":1,
        "motivo":"Motivo qualquer"
         }
        """;

        //ACT
        var response = mvc.perform(
            post("/adocoes")
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
    void deveriaDevolverCodigo400ParaSolicitarAdocaoComDadosInvalidos() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mvc.perform(
            post("/adocoes")
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





}