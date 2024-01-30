package br.com.fiap.techchallenge.servicopedido.core.usecases.produto;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.ProdutoRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa.ProdutoJpaRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.ProdutoMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Produto;
import br.com.fiap.techchallenge.servicopedido.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.servicopedido.core.dtos.AtualizaImagemProdutoDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AtualizaImagemProdutoUseCaseTest {

    @Mock
    private ProdutoJpaRepository produtoJpaRepository;
    private ProdutoMapper produtoMapper;
    private ProdutoRepository produtoRepository;
    private AtualizaImagemProdutoUseCase atualizaImagemProdutoUseCase;
    private Long id;
    private AtualizaImagemProdutoDTO imagemIn;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        produtoMapper = new ProdutoMapper();
        produtoRepository = new ProdutoRepository(produtoJpaRepository, produtoMapper);
        atualizaImagemProdutoUseCase = new AtualizaImagemProdutoUseCase(produtoRepository);

        id = 1L;
        imagemIn = new AtualizaImagemProdutoDTO(new byte[]{0, 1});
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve atualizar a imagem de um produto quando o id informado existir e os dados informados estiverem corretos")
    void deveAtualizarAImagemDeUmProduto_QuandoOIdInformadoExistirEDadosInformadosCorretos() {
        //Arrange
        Produto produtoSalvo = ProdutoHelper.criaProduto();
        when(produtoJpaRepository.findById(anyLong())).thenReturn(Optional.of(produtoSalvo));
        when(produtoJpaRepository.save(any(Produto.class))).thenReturn(produtoSalvo);

        //Act
        ProdutoDTO produtoDTO = atualizaImagemProdutoUseCase.atualizar(imagemIn, id);

        //Assert
        assertThat(produtoDTO).satisfies(produto -> {
            assertThat(produto).isNotNull();
            assertThat(produto.imagem()).isEqualTo(imagemIn.imagem());
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
        assertThatThrownBy(() -> atualizaImagemProdutoUseCase.atualizar(imagemIn, id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(message);

        verify(produtoJpaRepository, times(1)).findById(anyLong());
    }
}
