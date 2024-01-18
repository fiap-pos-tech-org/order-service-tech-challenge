# language: pt

Funcionalidade: Cliente

  Cenário: Criar um novo cliente
    Quando preencher todos os dados para cadastro do cliente
    Então o cliente deve ser criado com sucesso
    E deve exibir o cliente cadastrado

  Cenário: Alterar um cliente
    Dado que um cliente já está cadastrado
    Quando realizar a requisição para alterar o cliente
    Então o cliente deve ser alterado com sucesso
    E deve exibir o cliente alterado

  Cenário: Buscar todos os clientes
    Dado que um cliente já está cadastrado
    Quando requisitar a lista de todos os clientes
    Então os clientes devem ser exibidos com sucesso

  Cenário: Buscar um cliente por CPF
    Dado que um cliente já está cadastrado
    Quando realizar a busca do cliente
    Então o cliente deve ser exibido com sucesso
