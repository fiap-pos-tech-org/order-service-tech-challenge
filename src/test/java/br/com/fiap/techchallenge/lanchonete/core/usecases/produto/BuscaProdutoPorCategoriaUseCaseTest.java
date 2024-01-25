package br.com.fiap.techchallenge.lanchonete.core.usecases.produto;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.ProdutoRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.ProdutoJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ProdutoMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Produto;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.CategoriaEnum;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.lanchonete.utils.ProdutoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BuscaProdutoPorCategoriaUseCaseTest {
    @Mock
    private ProdutoJpaRepository produtoJpaRepository;
    private ProdutoMapper produtoMapper;
    private ProdutoRepository produtoRepository;
    private BuscaProdutoPorCategoriaUseCase buscaProdutoPorCategoriaUseCase;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        produtoMapper = new ProdutoMapper();
        produtoRepository = new ProdutoRepository(produtoJpaRepository, produtoMapper);
        buscaProdutoPorCategoriaUseCase = new BuscaProdutoPorCategoriaUseCase(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve buscar todos os produtos por categoria quando informada categoria válida")
    void deveBuscarTodosOsProdutosPorCategoria_QuandoInformadaCategoriaValida() {
        //Arrange
        Produto produtoSalvo = ProdutoHelper.criaProduto();
        when(produtoJpaRepository.findByCategoria(any(CategoriaEnum.class))).thenReturn(List.of(produtoSalvo));

        //Act
        List<ProdutoDTO> produtoDTOList = buscaProdutoPorCategoriaUseCase.buscarPorCategoria(CategoriaEnum.LANCHE);

        //Assert
        assertThat(produtoDTOList).allSatisfy(produto -> {
            assertThat(produto.nome()).isNotNull();
            assertThat(produto.categoria()).isNotNull();
            assertThat(produto.preco()).isNotNull();
            assertThat(produto.descricao()).isNotNull();
        });

        verify(produtoJpaRepository, times(1)).findByCategoria(any(CategoriaEnum.class));
    }
}
