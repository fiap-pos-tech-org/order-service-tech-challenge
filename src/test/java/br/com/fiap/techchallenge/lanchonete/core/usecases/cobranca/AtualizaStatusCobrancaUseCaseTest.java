package br.com.fiap.techchallenge.lanchonete.core.usecases.cobranca;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.CobrancaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.CobrancaJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.CobrancaMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Cobranca;
import br.com.fiap.techchallenge.lanchonete.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.lanchonete.core.ports.out.cobranca.AtualizaStatusCobrancaOutputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.out.cobranca.BuscaCobrancaOutputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.out.pedido.AtualizaStatusPedidoOutputPort;
import br.com.fiap.techchallenge.lanchonete.utils.CobrancaTesteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class AtualizaStatusCobrancaUseCaseTest {

    @Mock
    private CobrancaJpaRepository cobrancaJpaRepository;
    @Mock
    private BuscaCobrancaOutputPort buscaCobrancaOutputPort;
    @Mock
    private AtualizaStatusCobrancaOutputPort atualizaStatusCobrancaOutputPort;
    @Mock
    private AtualizaStatusPedidoOutputPort atualizaStatusPedidoOutputPort;
    @Mock
    private CobrancaMapper mapper;
    @InjectMocks
    private CobrancaRepository cobrancaRepository;

    private AtualizaStatusCobrancaUseCase cobrancaUseCase;
    private CobrancaDTO cobrancaDTO;
    private Cobranca cobrancaSalvo;
    private Cobranca cobrancaAtualizado;
    private final Long ID = 2L;
    private final Long NOT_FOUND_ID = 2000L;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        cobrancaUseCase = new AtualizaStatusCobrancaUseCase(
                buscaCobrancaOutputPort,
                atualizaStatusCobrancaOutputPort,
                atualizaStatusPedidoOutputPort
        );

        cobrancaSalvo = CobrancaTesteHelper.criaDefaultCobranca();
        cobrancaDTO = CobrancaTesteHelper.criaDefaultCobrancaDTO();
        cobrancaAtualizado = CobrancaTesteHelper.criaCopiaCobrancaDTO();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}
