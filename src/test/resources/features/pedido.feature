# language: pt

Funcionalidade: Pedido

  Cenário: Criar um novo pedido
    Dado que um cliente já está cadastrado
    E que um produto já está cadastrado
    Quando preencher todos os dados para cadastro do pedido
    Então o pedido deve ser criado com sucesso
    E deve exibir o pedido cadastrado

  Cenário: Buscar um pedido por id
    Dado que um cliente já está cadastrado
    E que um produto já está cadastrado
    E que um pedido já está cadastrado
    Quando realizar a busca do pedido por Id
    Então o pedido deve ser exibido com sucesso

  Cenário: Buscar pedidos por status
    Dado que um cliente já está cadastrado
    E que um produto já está cadastrado
    E que um pedido já está cadastrado
    Quando realizar a busca do pedido por status
    Então os pedidos devem ser exibidos com sucesso

  Cenário: Buscar todos os pedidos
    Dado que um cliente já está cadastrado
    E que um produto já está cadastrado
    E que um pedido já está cadastrado
    Quando requisitar a lista de todos os pedidos
    Então os pedidos devem ser exibidos com sucesso

  Cenário: Alterar o status de um pedido
    Dado que um cliente já está cadastrado
    E que um produto já está cadastrado
    E que um pedido já está cadastrado
    Quando realizar a requisição para alterar o pedido
    Então o pedido deve ser alterado com sucesso

  Cenário: Buscar pedidos para serem exibidos na fila de preparação
    Dado que um cliente já está cadastrado
    E que um produto já está cadastrado
    E que um pedido já está cadastrado
    E que um pedido já está no status em preparação
    Quando realizar a busca do pedido na fila de preparação
    Então os pedidos devem ser exibidos com sucesso

  @ignore
  Cenário: Buscar cobrança pelo id do pedido
    Dado que um pedido já está cadastrado
    Quando realizar a busca da cobrança pelo id do pedido
    Então o pedido deve ser exibido com sucesso