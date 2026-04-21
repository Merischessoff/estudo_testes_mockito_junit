package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService service;

    @Mock
    private AdocaoRepository repository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Spy
    List<ValidacaoSolicitacaoAdocao> validacaoSolicitacaoAdocaos = new ArrayList<>();

    @Mock
    private ValidacaoSolicitacaoAdocao validacaoSolicitacaoAdocao1;

    @Mock
    private ValidacaoSolicitacaoAdocao validacaoSolicitacaoAdocao2;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    private SolicitacaoAdocaoDto dto;

    @Captor
    private ArgumentCaptor<Adocao> adocaoArgumentCaptor;

    @Test
    void deveriaSalvarAdocaoAoSolicitar(){
        //ARRANGE
        this.dto = new SolicitacaoAdocaoDto(10l, 20l, "motivo");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        //ACT
        service.solicitar(dto);


        //ASSERT
        then(repository).should().save(adocaoArgumentCaptor.capture());
        Adocao adocaoCap = adocaoArgumentCaptor.getValue();
        assertEquals(pet, adocaoCap.getPet());
        assertEquals(tutor, adocaoCap.getTutor());
        assertEquals(dto.motivo(), adocaoCap.getMotivo());
    }

    @Test
    void deveriaChamarValidadoresAoSolicitar(){
        //ARRANGE
        this.dto = new SolicitacaoAdocaoDto(10l, 20l, "motivo");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        validacaoSolicitacaoAdocaos.add(validacaoSolicitacaoAdocao1);
        validacaoSolicitacaoAdocaos.add(validacaoSolicitacaoAdocao2);

        //ACT
        service.solicitar(dto);

        //ASSERT
        BDDMockito.then(validacaoSolicitacaoAdocao1).should().validar(dto);
        BDDMockito.then(validacaoSolicitacaoAdocao2).should().validar(dto);
    }
}