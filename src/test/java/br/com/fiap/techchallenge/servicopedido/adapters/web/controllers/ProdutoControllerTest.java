package br.com.fiap.techchallenge.servicopedido.adapters.web.controllers;

import br.com.fiap.techchallenge.servicopedido.adapters.web.ProdutoController;
import br.com.fiap.techchallenge.servicopedido.adapters.web.handlers.ExceptionsHandler;
import br.com.fiap.techchallenge.servicopedido.adapters.web.mappers.ProdutoMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.web.models.requests.ProdutoRequest;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.CategoriaEnum;
import br.com.fiap.techchallenge.servicopedido.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.servicopedido.core.dtos.AtualizaImagemProdutoDTO;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.produto.*;
import br.com.fiap.techchallenge.servicopedido.utils.ObjectParaJsonMapper;
import br.com.fiap.techchallenge.servicopedido.utils.ProdutoHelper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProdutoControllerTest {

    private MockMvc mockMvc;
    private AutoCloseable openMocks;
    @Mock
    private CriaProdutoInputPort criaProdutoInputPort;
    @Mock
    private AtualizaImagemProdutoInputPort atualizaImagemProdutoInputPort;
    @Mock
    private EditaProdutoInputPort editaProdutoInputPort;
    @Mock
    private RemoveProdutoInputPort removeProdutoInputPort;
    @Mock
    private BuscaProdutoPorIdInputPort buscaProdutoPorIdInputPort;
    @Mock
    private BuscaTodosProdutosInputPort buscaTodosProdutosInputPort;
    @Mock
    private BuscaProdutoPorCategoriaInputPort buscaProdutoPorCategoriaInputPort;
    @Mock
    private ProdutoMapper produtoMapper;
    private ProdutoRequest produtoRequest;
    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    void setUp() {
        openMocks = org.mockito.MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController)
                .setControllerAdvice(new ExceptionsHandler())
                .build();

        produtoRequest = ProdutoHelper.criarProdutoRequest();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    @DisplayName("Cadastra um produto")
    class CriaProduto {

        @Test
        @DisplayName("Deve cadastrar um produto quando os dados informados estiverem corretos")
        void deveCadastrarUmProduto_QuandoDadosInformadosCorretamente() throws Exception {
            //Arrange
            when(criaProdutoInputPort.criar(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoDTO());
            when(produtoMapper.toProdutoResponse(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoResponse());

            //Act
            //Assert
            mockMvc.perform(post("/produtos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(produtoRequest))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpectAll(
                            jsonPath("$.id").isNotEmpty(),
                            jsonPath("$.id").isNumber(),
                            jsonPath("$.nome").isNotEmpty(),
                            jsonPath("$.nome").isString(),
                            jsonPath("$.categoria").isNotEmpty(),
                            jsonPath("$.categoria").isString(),
                            jsonPath("$.preco").isNotEmpty(),
                            jsonPath("$.preco").isNumber(),
                            jsonPath("$.descricao").isNotEmpty(),
                            jsonPath("$.descricao").isString()

                    );
        }

        @Test
        @DisplayName("Deve retornar bad request quando o nome do produto for nulo ou vazio")
        void deveRetornarBadRequest_QuandoNomeProdutoForNuloOuVazio() throws Exception {
            //Arrange
            produtoRequest.setNome(null);
            when(criaProdutoInputPort.criar(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoDTO());
            when(produtoMapper.toProdutoResponse(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoResponse());

            //Act
            //Assert
            mockMvc.perform(post("/produtos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(produtoRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("O campo 'nome' é obrigatório"));
        }

        @Test
        @DisplayName("Deve retornar bad request quando a categoria do produto for nula")
        void deveRetornarBadRequest_QuandoCategoriaProdutoForNula() throws Exception {
            //Arrange
            produtoRequest.setCategoria(null);
            when(criaProdutoInputPort.criar(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoDTO());
            when(produtoMapper.toProdutoResponse(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoResponse());

            //Act
            //Assert
            mockMvc.perform(post("/produtos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(produtoRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("O campo 'categoria' é obrigatório"));
        }

        @Test
        @DisplayName("Deve retornar bad request quando o preço do produto for nulo")
        void deveRetornarBadRequest_QuandoPrecoProdutoForNulo() throws Exception {
            //Arrange
            produtoRequest.setPreco(null);
            when(criaProdutoInputPort.criar(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoDTO());
            when(produtoMapper.toProdutoResponse(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoResponse());

            //Act
            //Assert
            mockMvc.perform(post("/produtos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(produtoRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("O campo 'preco' é obrigatório"));
        }

        @Test
        @DisplayName("Deve retornar bad request quando a descrição do produto for nula")
        void deveRetornarBadRequest_QuandoDescricaoProdutoForNula() throws Exception {
            //Arrange
            produtoRequest.setDescricao(null);
            when(criaProdutoInputPort.criar(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoDTO());
            when(produtoMapper.toProdutoResponse(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoResponse());

            //Act
            //Assert
            mockMvc.perform(post("/produtos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(produtoRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("O campo 'descricao' é obrigatório"));
        }
    }

    @Nested
    @DisplayName("Edita um produto")
    class EditaProduto {

        @Test
        @DisplayName("Deve editar um produto quando os dados informados estiverem corretos")
        void deveEditarUmProduto_QuandoDadosInformadosCorretamente() throws Exception {
            //Arrange
            when(editaProdutoInputPort.editar(any(ProdutoDTO.class), anyLong())).thenReturn(ProdutoHelper.criaProdutoDTO());
            when(produtoMapper.toProdutoResponse(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoResponse());

            //Act
            //Assert
            mockMvc.perform(put("/produtos/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(produtoRequest))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$.id").isNotEmpty(),
                            jsonPath("$.id").isNumber(),
                            jsonPath("$.nome").isNotEmpty(),
                            jsonPath("$.nome").isString(),
                            jsonPath("$.categoria").isNotEmpty(),
                            jsonPath("$.categoria").isString(),
                            jsonPath("$.preco").isNotEmpty(),
                            jsonPath("$.preco").isNumber(),
                            jsonPath("$.descricao").isNotEmpty(),
                            jsonPath("$.descricao").isString()

                    );
        }

        @Test
        @DisplayName("Deve retornar Not Found quando id do produto não existir")
        void deveRetornarNotFound_QuandoIdProdutoNaoExistir() throws Exception {
            //Arrange
            Long id = 1L;
            String mensagem = String.format("Produto com o id %s não existe", id);
            when(editaProdutoInputPort.editar(any(ProdutoDTO.class), anyLong())).thenThrow(new EntityNotFoundException(mensagem));

            //Act
            //Assert
            mockMvc.perform(put("/produtos/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(produtoRequest)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value(mensagem));

        }

    }

    @Nested
    @DisplayName("Remove um produto")
    class RemoveProduto {

        @Test
        @DisplayName("Deve remover um produto quando o id informado existir")
        void deveRemoverUmProduto_QuandoIdInformadoExistir() throws Exception {
            //Arrange
            when(removeProdutoInputPort.remover(anyLong())).thenReturn(ProdutoHelper.criaProdutoDTO());
            when(produtoMapper.toProdutoResponse(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoResponse());

            //Act
            //Assert
            mockMvc.perform(delete("/produtos/{id}", 1L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$.id").isNotEmpty(),
                            jsonPath("$.id").isNumber(),
                            jsonPath("$.nome").isNotEmpty(),
                            jsonPath("$.nome").isString(),
                            jsonPath("$.categoria").isNotEmpty(),
                            jsonPath("$.categoria").isString(),
                            jsonPath("$.preco").isNotEmpty(),
                            jsonPath("$.preco").isNumber(),
                            jsonPath("$.descricao").isNotEmpty(),
                            jsonPath("$.descricao").isString()

                    );
        }

        @Test
        @DisplayName("Deve retornar Not Found quando id do produto não existir")
        void deveRetornarNotFound_QuandoIdProdutoNaoExistir() throws Exception {
            //Arrange
            Long id = 1L;
            String mensagem = String.format("Produto com o id %s não existe", id);
            when(removeProdutoInputPort.remover(anyLong())).thenThrow(new EntityNotFoundException(mensagem));

            //Act
            //Assert
            mockMvc.perform(delete("/produtos/{id}", id)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value(mensagem));

        }
    }

    @Nested
    @DisplayName("Busca produtos")
    class BuscaProdutos {

        @Test
        @DisplayName("Deve buscar um produto quando o id informado existir")
        void deveBuscarUmProduto_QuandoIdInformadoExistir() throws Exception {
            //Arrange
            when(buscaProdutoPorIdInputPort.buscarPorId(anyLong())).thenReturn(ProdutoHelper.criaProdutoDTO());
            when(produtoMapper.toProdutoResponse(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoResponse());

            //Act
            //Assert
            mockMvc.perform(get("/produtos/{id}", 1L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$.id").isNotEmpty(),
                            jsonPath("$.id").isNumber(),
                            jsonPath("$.nome").isNotEmpty(),
                            jsonPath("$.nome").isString(),
                            jsonPath("$.categoria").isNotEmpty(),
                            jsonPath("$.categoria").isString(),
                            jsonPath("$.preco").isNotEmpty(),
                            jsonPath("$.preco").isNumber(),
                            jsonPath("$.descricao").isNotEmpty(),
                            jsonPath("$.descricao").isString()

                    );
        }

        @Test
        @DisplayName("Deve retornar Not Found quando id do produto não existir")
        void deveRetornarNotFound_QuandoIdProdutoNaoExistir() throws Exception {
            //Arrange
            Long id = 1L;
            String mensagem = String.format("Produto com o id %s não existe", id);
            when(buscaProdutoPorIdInputPort.buscarPorId(anyLong())).thenThrow(new EntityNotFoundException(mensagem));

            //Act
            //Assert
            mockMvc.perform(get("/produtos/{id}", id)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value(mensagem));

        }

        @Test
        @DisplayName("Deve buscar todos os produtos")
        void deveBuscarTodosOsProdutos() throws Exception {
            //Arrange
            when(buscaTodosProdutosInputPort.buscartodos()).thenReturn(ProdutoHelper.criaListaProdutoDTO());
            when(produtoMapper.toProdutoResponse(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoResponse());

            //Act
            //Assert
            mockMvc.perform(get("/produtos")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].id").isNotEmpty())
                    .andExpect(jsonPath("$[0].id").isNumber());
        }

        @Test
        @DisplayName("Deve buscar todos os produtos por categoria")
        void deveBuscarTodosOsProdutosPorCategoria() throws Exception {
            //Arrange
            when(buscaProdutoPorCategoriaInputPort.buscarPorCategoria(any(CategoriaEnum.class))).thenReturn(ProdutoHelper.criaListaProdutoDTO());
            when(produtoMapper.toProdutoResponse(any(ProdutoDTO.class))).thenReturn(ProdutoHelper.criaProdutoResponse());

            //Act
            //Assert
            mockMvc.perform(get("/produtos/categoria/{categoria}", "BEBIDA")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$").isArray(),
                            jsonPath("$[0].id").isNotEmpty(),
                            jsonPath("$[0].id").isNumber(),
                            jsonPath("$[0].nome").isNotEmpty(),
                            jsonPath("$[0].nome").isString(),
                            jsonPath("$[0].categoria").isNotEmpty(),
                            jsonPath("$[0].categoria").isString(),
                            jsonPath("$[0].preco").isNotEmpty(),
                            jsonPath("$[0].preco").isNumber(),
                            jsonPath("$[0].descricao").isNotEmpty(),
                            jsonPath("$[0].descricao").isString()
                    );
        }
    }

    @Nested
    @DisplayName("Atualiza a imagem de um produto")
    class AtualizaImagemProduto {

        @Test
        @DisplayName("Deve atualizar a imagem de um produto quando a imagem for informada corretamente")
        void deveAtualizarImagemProduto_QuandoImagemValidaInformadaCorretamente() throws Exception {
            //Arrange
            when(atualizaImagemProdutoInputPort.atualizar(any(AtualizaImagemProdutoDTO.class), anyLong())).thenReturn(ProdutoHelper.criaProdutoDTO());

            //Act
            //Assert
            mockMvc.perform(multipart(HttpMethod.PATCH, "/produtos/{id}", 1L)
                            .file("imagem", "imagem".getBytes()))
                    .andExpect(status().isNoContent());
        }
    }
}
