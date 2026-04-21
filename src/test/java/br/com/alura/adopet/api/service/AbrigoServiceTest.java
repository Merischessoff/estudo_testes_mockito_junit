package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService service;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private PetRepository petRepository;


    @Test
    void deveRetornarUmaListaDePetsValidos(){
        //ARRANGE
        var nomeAbrigo = "abrigo";
        var abrigoFake = new Abrigo();

        when(abrigoRepository.findByNome(nomeAbrigo)).thenReturn(Optional.of(abrigoFake));
        when(petRepository.findByAbrigo(abrigoFake)).thenReturn(new ArrayList<>());

        //ACT
        var resultado = service.listarPetsDoAbrigo(nomeAbrigo);

        //ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

    }

    @Test
    void deveriacarregarAbrigo() {
        // ARRANGE
        String nomeAbrigo = "Abrigo Amigo";
        Abrigo abrigoFake = new Abrigo();

        // Configuramos o mock para retornar o abrigo quando o service procurar por ele
        given(abrigoRepository.findByNome(nomeAbrigo)).willReturn(Optional.of(abrigoFake));

        // ACT
        // Se o método retorna o abrigo, guardamos na variável 'resultado'
        Abrigo resultado = service.carregarAbrigo(nomeAbrigo);

        // ASSERT
        // 1. Verifica se o repositório foi chamado com o nome correto
        then(abrigoRepository).should().findByNome(nomeAbrigo);

        // 2. Verifica se o objeto retornado é o mesmo que o mock entregou
        assertEquals(abrigoFake, resultado);
    }
}