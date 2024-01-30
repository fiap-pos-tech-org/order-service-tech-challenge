package br.com.fiap.techchallenge.servicopedido.adapters.repository;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa.PedidoJpaRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.servicopedido.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.BuscaTodosPedidosOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.BuscarPedidoPorIdOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.CriaPedidoOutputPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PedidoRepository implements CriaPedidoOutputPort, BuscaTodosPedidosOutputPort, BuscarPedidoPorIdOutputPort {
    private final PedidoMapper pedidoMapper;
    private final PedidoJpaRepository pedidoJpaRepository;

    public PedidoRepository(PedidoMapper pedidoMapper, PedidoJpaRepository pedidoJpaRepository) {
        this.pedidoMapper = pedidoMapper;
        this.pedidoJpaRepository = pedidoJpaRepository;
    }

    @Override
    public List<PedidoDTO> buscarTodos() {
        var listaPedidos = pedidoJpaRepository.findAll();
        return listaPedidos.stream().map(pedidoMapper::toPedidoDTO).toList();
    }

    @Override
    public PedidoDTO criar(PedidoDTO pedidoIn) {
        var pedido = pedidoMapper.toPedido(pedidoIn);
        var pedidoSalvo = pedidoJpaRepository.save(pedido);
        return pedidoMapper.toPedidoDTO(pedidoSalvo);
    }

    @Override
    public PedidoDTO buscarPorId(Long id) {
        var pedidoBuscado = buscarPedidoPorId(id);
        return pedidoMapper.toPedidoDTO(pedidoBuscado);
    }


    private br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Pedido buscarPedidoPorId(Long id) {
        return pedidoJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido " + id + " não encontrado"));
    }

}
