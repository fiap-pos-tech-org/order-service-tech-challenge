package br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteJpaRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCpf(String cpf);
}
