package br.com.fiap.techchallenge.servicopedido.core.usecases.produto;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.ProdutoRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa.ProdutoJpaRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.ProdutoMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Produto;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.CategoriaEnum;
import br.com.fiap.techchallenge.servicopedido.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.servicopedido.utils.ProdutoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class EditaProdutoUseCaseTest {
    @Mock
    private ProdutoJpaRepository produtoJpaRepository;
    private ProdutoMapper produtoMapper;
    private ProdutoRepository produtoRepository;
    private EditaProdutoUseCase editaProdutoUseCase;
    private Long id;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        produtoMapper = new ProdutoMapper();
        produtoRepository = new ProdutoRepository(produtoJpaRepository, produtoMapper);
        editaProdutoUseCase = new EditaProdutoUseCase(produtoRepository);

        id = 1L;
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve editar um produto quando todos os atributos informados corretamente")
    void deveEditarUmProduto_QuandoTodosOsAtributosInformadosCorretamente() {
        //Arrange
        Produto produtoSalvo = ProdutoHelper.criaProduto();
        when(produtoJpaRepository.findById(anyLong())).thenReturn(Optional.of(produtoSalvo));
        when(produtoJpaRepository.save(any(Produto.class))).thenReturn(produtoSalvo);

        //Act
        ProdutoDTO produtoDTO = editaProdutoUseCase.editar(ProdutoHelper.criaProdutoDTO(), id);

        //Assert
        assertThat(produtoDTO).satisfies(produto -> {
            assertThat(produto.id()).isEqualTo(id);
            assertThat(produto.nome()).isEqualTo("dogão-brabo");
            assertThat(produto.categoria()).isEqualTo(CategoriaEnum.LANCHE);
            assertThat(produto.preco()).isEqualTo(BigDecimal.valueOf(6));
            assertThat(produto.descricao()).isEqualTo("topperson");
        });

        verify(produtoJpaRepository, times(1)).findById(anyLong());
        verify(produtoJpaRepository, times(1)).save(any(Produto.class));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando id do produto informado não existir")
    void deveLancarEntityNotFoundException_QuandoIdProdutoInformadoNaoExistir() {
        //Arrange
        String message = String.format("Produto com o id %d não existe", id);
        when(produtoJpaRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Act
        //Assert
        assertThatThrownBy(() -> editaProdutoUseCase.editar(ProdutoHelper.criaProdutoDTO(), id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(message);

        verify(produtoJpaRepository, times(1)).findById(anyLong());
    }
}
