package br.com.fiap.techchallenge.lanchonete.core.usecases.produto;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.ProdutoRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.ProdutoJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ProdutoMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Produto;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.CategoriaEnum;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.lanchonete.utils.ProdutoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BuscaProdutoPorIdUseCaseTest {
    @Mock
    private ProdutoJpaRepository produtoJpaRepository;
    private ProdutoMapper produtoMapper;
    private ProdutoRepository produtoRepository;
    private BuscaProdutoPorIdUseCase buscaProdutoPorIdUseCase;
    private Long id;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        produtoMapper = new ProdutoMapper();
        produtoRepository = new ProdutoRepository(produtoJpaRepository, produtoMapper);
        buscaProdutoPorIdUseCase = new BuscaProdutoPorIdUseCase(produtoRepository);

        id = 1L;
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve buscar produto por id quando informada id válido")
    void deveBuscarProdutoPorId_QuandoInformadaIdValido() {
        //Arrange
        Produto produtoSalvo = ProdutoHelper.criaProduto();
        when(produtoJpaRepository.findById(anyLong())).thenReturn(Optional.of(produtoSalvo));

        //Act
        ProdutoDTO produtoDTO = buscaProdutoPorIdUseCase.buscarPorId(id);

        //Assert
        assertThat(produtoDTO).satisfies(produto -> {
            assertThat(produto.id()).isNotNull();
            assertThat(produto.nome()).isNotNull();
            assertThat(produto.categoria()).isNotNull();
            assertThat(produto.preco()).isNotNull();
            assertThat(produto.descricao()).isNotNull();
        });

        verify(produtoJpaRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando id do produto informado não existir")
    void deveLancarEntityNotFoundException_QuandoIdProdutoInformadoNaoExistir() {
        //Arrange
        String message = String.format("Produto com o id %d não existe", id);
        when(produtoJpaRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Act
        //Assert
        assertThatThrownBy(() -> buscaProdutoPorIdUseCase.buscarPorId(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(message);

        verify(produtoJpaRepository, times(1)).findById(anyLong());
    }
}
