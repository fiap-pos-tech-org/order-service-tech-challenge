# language: pt

Funcionalidade: Cobrança

  Cenário: Criar uma nova cobrança
    Dado que um cliente já está cadastrado
    E que um produto já está cadastrado
    E que um pedido já está cadastrado
    Quando preencher todos os dados para cadastro da cobrança
    Então a cobrança deve ser criada com sucesso
    E deve exibir a cobrança cadastrada

  Cenário: Buscar uma cobrança
    Dado que um cliente já está cadastrado
    E que um produto já está cadastrado
    E que um pedido já está cadastrado
    E que uma cobrança já está cadastrada
    Quando realizar a busca da cobrança
    Então a cobrança deve ser exibida com sucesso

  Cenário: Alterar uma cobrança
    Dado que um cliente já está cadastrado
    E que um produto já está cadastrado
    E que um pedido já está cadastrado
    E que uma cobrança já está cadastrada
    Quando realizar a requisição para alterar a cobrança
    Então a cobrança deve ser alterada com sucesso
    E deve exibir a cobrança alterada

  Cenário: Alterar uma nova cobrança no Mercado Pago
    Dado que um cliente já está cadastrado
    E que um produto já está cadastrado
    E que um pedido já está cadastrado
    E que uma cobrança já está cadastrada
    Quando realizar a requisição para alterar a cobrança no Mercado Pago
    Então a cobrança deve ser alterada com sucesso no Mercado Pago
    E a cobrança deve ser exibida com resposta vazia
