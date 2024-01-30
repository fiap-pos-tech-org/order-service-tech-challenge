package br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoJpaRepository extends JpaRepository<ItemPedido, Long> {

    List<ItemPedido> findAllByPedidoId(Long pedidoid);
}
