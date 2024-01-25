package br.com.fiap.techchallenge.lanchonete.core.usecases.produto;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.ProdutoRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.ProdutoJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ProdutoMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Produto;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.lanchonete.utils.ProdutoHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CriaProdutoUseCaseTest {

    @Mock
    private ProdutoJpaRepository produtoJpaRepository;
    private ProdutoMapper produtoMapper;
    private ProdutoRepository produtoRepository;
    private CriaProdutoUseCase criaProdutoUseCase;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        produtoMapper = new ProdutoMapper();
        produtoRepository = new ProdutoRepository(produtoJpaRepository, produtoMapper);
        criaProdutoUseCase = new CriaProdutoUseCase(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveCriarProduto_QuandoTodosOsAtributosInformadosCorretamente() {
        //Arrange
        when(produtoJpaRepository.save(any(Produto.class))).thenReturn(ProdutoHelper.criaProdutoCopiaDTO());

        //Act
        ProdutoDTO produtoDTO = criaProdutoUseCase.criar(ProdutoHelper.criaProdutoDTO());

        //Assert
        assertThat(produtoDTO).satisfies(produto -> {
            assertThat(produto).isNotNull();
            assertThat(produto.id()).isNotNull();
            assertThat(produto.nome()).isNotNull();
            assertThat(produto.descricao()).isNotNull();
            assertThat(produto.preco()).isNotNull();
            assertThat(produto.categoria()).isNotNull();
        });

        verify(produtoJpaRepository, times(1)).save(any(Produto.class));
    }
}
