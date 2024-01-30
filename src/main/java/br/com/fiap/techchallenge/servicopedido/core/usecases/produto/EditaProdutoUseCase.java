package br.com.fiap.techchallenge.servicopedido.core.usecases.produto;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.produto.EditaProdutoInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.produto.EditaProdutoOutputPort;

public class EditaProdutoUseCase implements EditaProdutoInputPort {

    EditaProdutoOutputPort editaProdutoOutputPort;

    public EditaProdutoUseCase(EditaProdutoOutputPort editaProdutoOutputPort) {
        this.editaProdutoOutputPort = editaProdutoOutputPort;
    }

    @Override
    public ProdutoDTO editar(ProdutoDTO produtoIn, Long id) {
        return editaProdutoOutputPort.editar(produtoIn, id);
    }
}
