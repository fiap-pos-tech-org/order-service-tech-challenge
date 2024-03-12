package br.com.fiap.techchallenge.servicopedido.config;

import br.com.fiap.techchallenge.servicopedido.core.ports.in.cliente.AtualizaClienteInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.cliente.BuscaClientePorIdOuCpfInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.cliente.BuscaTodosClientesInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.cliente.CadastraClienteInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.*;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.produto.*;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente.AtualizaClienteOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente.BuscaClienteOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente.BuscaTodosClientesOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente.CadastraClienteOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.*;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.produto.*;
import br.com.fiap.techchallenge.servicopedido.core.usecases.cliente.AtualizaClienteUseCase;
import br.com.fiap.techchallenge.servicopedido.core.usecases.cliente.BuscaClientePorIdOuCpfIdOuCpfUseCase;
import br.com.fiap.techchallenge.servicopedido.core.usecases.cliente.BuscaTodosClientesUseCase;
import br.com.fiap.techchallenge.servicopedido.core.usecases.cliente.CadastraClienteUseCase;
import br.com.fiap.techchallenge.servicopedido.core.usecases.pedido.*;
import br.com.fiap.techchallenge.servicopedido.core.usecases.produto.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreInjectionConfig {

    @Bean
    CriaProdutoInputPort criarProduto(CriaProdutoOutputPort criaProdutoOutputPort) {
        return new CriaProdutoUseCase(criaProdutoOutputPort);
    }

    @Bean
    AtualizaImagemProdutoInputPort criarImagemProduto(AtualizaImagemProdutoOutputPort atualizaImagemProdutoOutputPort) {
        return new AtualizaImagemProdutoUseCase(atualizaImagemProdutoOutputPort);
    }

    @Bean
    EditaProdutoInputPort editarProduto(EditaProdutoOutputPort editaProdutoOutputPort) {
        return new EditaProdutoUseCase(editaProdutoOutputPort);
    }

    @Bean
    RemoveProdutoInputPort removerProduto(RemoveProdutoOutputPort removeProdutoOutputPort) {
        return new RemoveProdutoUseCase(removeProdutoOutputPort);
    }

    @Bean
    BuscaProdutoPorIdInputPort buscarProdutoPorId(BuscaProdutoPorIdOutputPort buscaProdutoPorIdOutputPort) {
        return new BuscaProdutoPorIdUseCase(buscaProdutoPorIdOutputPort);
    }

    @Bean
    BuscaTodosProdutosInputPort buscarTodos(BuscaTodosProdutosOutputPort buscaProdutoPorIdOutputPort) {
        return new BuscaTodosProdutosUseCase(buscaProdutoPorIdOutputPort);
    }

    @Bean
    BuscaProdutoPorCategoriaInputPort buscarPorCategoria(BuscaProdutoPorCategoriaOutputPort buscaProdutoPorIdOutputPort) {
        return new BuscaProdutoPorCategoriaUseCase(buscaProdutoPorIdOutputPort);
    }

    @Bean
    AtualizaClienteInputPort atualizaCliente(AtualizaClienteOutputPort atualizaClienteOutputPort) {
        return new AtualizaClienteUseCase(atualizaClienteOutputPort);
    }

    @Bean
    BuscaClientePorIdOuCpfInputPort buscaClientePorCpf(BuscaClienteOutputPort buscaClienteOutputPort) {
        return new BuscaClientePorIdOuCpfIdOuCpfUseCase(buscaClienteOutputPort);
    }

    @Bean
    BuscaTodosClientesInputPort buscaTodosClientes(BuscaTodosClientesOutputPort buscaTodosClientesOutputPort) {
        return new BuscaTodosClientesUseCase(buscaTodosClientesOutputPort);
    }

    @Bean
    CadastraClienteInputPort cadastraCliente(CadastraClienteOutputPort cadastraClienteOutputPort) {
        return new CadastraClienteUseCase(cadastraClienteOutputPort);
    }

    @Bean
    CriaPedidoInputPort criarPedido(
            CriaPedidoOutputPort criaPedidoOutputPort,
            BuscaProdutoPorIdOutputPort buscaProdutoPorIdOutputPort,
            BuscaClienteOutputPort buscaClienteOutputPort
    ) {
        return new CriaPedidoUseCase(criaPedidoOutputPort, buscaProdutoPorIdOutputPort, buscaClienteOutputPort);
    }

    @Bean
    AtualizaStatusPedidoInputPort atualizaStatusPedido(AtualizaStatusPedidoOutputPort atualizaStatusPedidoOutputPort){
        return new AtualizaStatusPedidoUseCase(atualizaStatusPedidoOutputPort);
    }

    @Bean
    BuscarPedidoPorIdInputPort buscarPedidoPorId(BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort) {
        return new BuscarPedidoPorIdUseCase(buscarPedidoPorIdOutputPort);
    }

    @Bean
    BuscaTodosPedidosInputPort buscarTodosPedidos(BuscaTodosPedidosOutputPort buscaTodosPedidosOutputPort) {
        return new BuscaTodosPedidosUseCase(buscaTodosPedidosOutputPort);
    }

    @Bean
    PublicaPedidoInputPort publicaPedidoInputPort(PublicaPedidoOutputPort publicaPedidoOutputPort) {
        return new PublicaPedidoUseCase(publicaPedidoOutputPort);
    }
}
