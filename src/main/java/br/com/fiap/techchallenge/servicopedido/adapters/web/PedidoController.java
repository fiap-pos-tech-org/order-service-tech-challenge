package br.com.fiap.techchallenge.servicopedido.adapters.web;

import br.com.fiap.techchallenge.servicopedido.adapters.web.mappers.PedidoMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.web.models.requests.PedidoRequest;
import br.com.fiap.techchallenge.servicopedido.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.BuscaTodosPedidosInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.BuscarPedidoPorIdInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.CriaPedidoInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pedido", description = "APIs para gerenciamento de Pedido")
@RestController
@RequestMapping("/pedidos")
public class PedidoController extends ControllerBase {
    private final CriaPedidoInputPort criaPedidoInputPort;
    private final BuscaTodosPedidosInputPort buscaTodosPedidosInputPort;
    private final BuscarPedidoPorIdInputPort buscarPedidoPorIdInputPort;
    private final PedidoMapper pedidoMapper;

    public PedidoController(CriaPedidoInputPort criaPedidoInputPort,
                            BuscaTodosPedidosInputPort buscaTodosPedidosInputPort,
                            BuscarPedidoPorIdInputPort buscarPedidoPorIdInputPort,
                            PedidoMapper pedidoMapper
    ) {
        this.criaPedidoInputPort = criaPedidoInputPort;
        this.buscaTodosPedidosInputPort = buscaTodosPedidosInputPort;
        this.buscarPedidoPorIdInputPort = buscarPedidoPorIdInputPort;
        this.pedidoMapper = pedidoMapper;
    }

    @Operation(summary = "Busca todos os pedidos")
    @GetMapping
    public ResponseEntity<List<PedidoResponse>> buscarTodos() {
        var pedidosOut = buscaTodosPedidosInputPort.buscarTodos();
        var listPedidoResponse = pedidoMapper.toPedidoListResponse(pedidosOut);
        return ResponseEntity.ok(listPedidoResponse);
    }

    @Operation(summary = "Busca pedido pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable("id") Long id) {
        var pedidoOut = buscarPedidoPorIdInputPort.buscarPorId(id);
        var pedidoResponse = pedidoMapper.toPedidoResponse(pedidoOut);
        return ResponseEntity.ok(pedidoResponse);
    }

    @Operation(summary = "Cria um pedido")
    @PostMapping
    public ResponseEntity<PedidoResponse> criarPedido(@Valid @RequestBody PedidoRequest pedidoRequest) {
        var pedidoOut = criaPedidoInputPort.criar(pedidoRequest.toCriaItemPedidoDTO());
        var pedidoResponse = pedidoMapper.toPedidoResponse(pedidoOut);
        var uri = getExpandedCurrentUri("/{id}", pedidoResponse.getId());
        return ResponseEntity.created(uri).body(pedidoResponse);
    }

}
