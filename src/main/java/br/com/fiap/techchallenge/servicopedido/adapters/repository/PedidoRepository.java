package br.com.fiap.techchallenge.servicopedido.adapters.repository;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa.PedidoJpaRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicopedido.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoPagamentoDTO;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.PublicaPedidoInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.AtualizaStatusPedidoOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.BuscaTodosPedidosOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.BuscarPedidoPorIdOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.CriaPedidoOutputPort;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PedidoRepository implements CriaPedidoOutputPort, BuscaTodosPedidosOutputPort, BuscarPedidoPorIdOutputPort,
        AtualizaStatusPedidoOutputPort {

    @Value("${aws.sns.topico-pagamento-pendente-arn}")
    private String topicPagamentoPendenteArn;
    private final PedidoMapper pedidoMapper;
    private final PedidoJpaRepository pedidoJpaRepository;
    private final PublicaPedidoInputPort publicaPedidoInputPort;

    public PedidoRepository(PedidoMapper pedidoMapper, PedidoJpaRepository pedidoJpaRepository, PublicaPedidoInputPort publicaPedidoInputPort) {
        this.pedidoMapper = pedidoMapper;
        this.pedidoJpaRepository = pedidoJpaRepository;
        this.publicaPedidoInputPort = publicaPedidoInputPort;
    }

    @Override
    public List<PedidoDTO> buscarTodos() {
        var listaPedidos = pedidoJpaRepository.findAll();
        return listaPedidos.stream().map(pedidoMapper::toPedidoDTO).toList();
    }

    @Override
    @Transactional
    public PedidoDTO criar(PedidoDTO pedidoIn) {
        var pedido = pedidoMapper.toPedido(pedidoIn);
        var pedidoSalvo = pedidoJpaRepository.save(pedido);
        var mensagem = new MensagemPedidoPagamentoDTO(
                pedidoSalvo.getId(),
                pedidoSalvo.getCliente().getId(),
                pedidoSalvo.getValorTotal()
        );

        publicaPedidoInputPort.publicar(mensagem, topicPagamentoPendenteArn);
        return pedidoMapper.toPedidoDTO(pedidoSalvo);
    }

    @Override
    public PedidoDTO atualizarStatus(Long id, StatusPedidoEnum status) {
        var pedidoBuscado = buscarPedidoPorId(id);
        pedidoBuscado.setStatus(status);
        var pedido = pedidoJpaRepository.save(pedidoBuscado);
        return pedidoMapper.toPedidoDTO(pedido);
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
