package br.com.fiap.techchallenge.servicopedido.core.usecases.produto;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.ProdutoRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa.ProdutoJpaRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.ProdutoMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Produto;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.servicopedido.utils.ProdutoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
