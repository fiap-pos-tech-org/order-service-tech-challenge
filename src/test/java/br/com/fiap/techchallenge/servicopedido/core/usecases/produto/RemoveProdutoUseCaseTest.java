package br.com.fiap.techchallenge.servicopedido.core.usecases.produto;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.ProdutoRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa.ProdutoJpaRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.ProdutoMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Produto;
import br.com.fiap.techchallenge.servicopedido.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.servicopedido.utils.ProdutoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class RemoveProdutoUseCaseTest {
    @Mock
    private ProdutoJpaRepository produtoJpaRepository;
    private ProdutoMapper produtoMapper;
    private ProdutoRepository produtoRepository;
    private RemoveProdutoUseCase removeProdutoUseCase;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        produtoMapper = new ProdutoMapper();
        produtoRepository = new ProdutoRepository(produtoJpaRepository, produtoMapper);
        removeProdutoUseCase = new RemoveProdutoUseCase(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve remover um produto quando o id informado existir")
    void deveRemoverUmProduto_QuandoIdInformadoExistir() {
        //Arrange
        Produto produtoSalvo = ProdutoHelper.criaProduto();
        when(produtoJpaRepository.findById(anyLong())).thenReturn(Optional.of(produtoSalvo));
        doNothing().when(produtoJpaRepository).delete(any(Produto.class));

        //Act
        ProdutoDTO produtoDTO = removeProdutoUseCase.remover(1L);

        //Assert
        assertThat(produtoDTO).satisfies(produto -> {
            assertThat(produto).isNotNull();
            assertThat(produto.imagem()).isNull();
        });

        verify(produtoJpaRepository, times(1)).findById(anyLong());
        verify(produtoJpaRepository, times(1)).delete(any(Produto.class));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando id do produto informado não existir")
    void deveLancarEntityNotFoundException_QuandoIdProdutoInformadoNaoExistir() {
        //Arrange
        Long id = 1L;
        String message = String.format("Produto com o id %d não existe", id);
        when(produtoJpaRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Act
        //Assert
        assertThatThrownBy(() -> removeProdutoUseCase.remover(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(message);

        verify(produtoJpaRepository, times(1)).findById(anyLong());
    }
}
