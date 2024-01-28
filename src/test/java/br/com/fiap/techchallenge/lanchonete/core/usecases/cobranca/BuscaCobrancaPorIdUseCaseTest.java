package br.com.fiap.techchallenge.lanchonete.core.usecases.cobranca;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.CobrancaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.CobrancaJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.CobrancaMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Cobranca;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.lanchonete.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.lanchonete.utils.CobrancaHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BuscaCobrancaPorIdUseCaseTest {

    @Mock
    private CobrancaJpaRepository cobrancaJpaRepository;
    @Mock
    private CobrancaMapper cobrancaMapper;
    @InjectMocks
    private CobrancaRepository cobrancaRepository;
    private BuscaCobrancaPorIdUseCase cobrancaUseCase;

    private CobrancaDTO cobrancaDTO;
    private Cobranca cobrancaSalvo;
    private final Long ID = 2L;
    private final Long NOT_FOUND_ID = 2000L;
    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);

        cobrancaUseCase = new BuscaCobrancaPorIdUseCase(cobrancaRepository);
        cobrancaSalvo = CobrancaHelper.criaCobranca();
        cobrancaDTO = CobrancaHelper.criaCobrancaDTO();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve buscar uma cobrança pelo id")
    void deveBuscarCobranca_QuandoInformadoUmId() {
        //Arrange
        when(cobrancaJpaRepository.findById(ID)).thenReturn(Optional.of(cobrancaSalvo));
        when(cobrancaMapper.toCobrancaOut(any(Cobranca.class))).thenReturn(cobrancaDTO);

        //Act
        CobrancaDTO cobranca = cobrancaUseCase.buscarPorId(ID);

        //Assert
        assertThat(cobranca).isNotNull();
        assertThat(cobranca).satisfies(c -> {
            c.id().equals(1L);
            c.pedidoId().equals(1L);
            c.status().equals(StatusCobrancaEnum.PAGO);
        });

        verify(cobrancaJpaRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Deve lancar EntityNotFoundException quando informado um id inexistente")
    void deveLancarEntityNotFoundException_QuandoInformadoUmIdInexistente() {
        //Arrange
        String message = String.format("Cobrança com o id %s não existe", NOT_FOUND_ID);
        when(cobrancaJpaRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Act
        //Assert
        assertThatThrownBy(() -> cobrancaUseCase.buscarPorId(NOT_FOUND_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(message);

        verify(cobrancaJpaRepository, times(1)).findById(anyLong());
    }
}
