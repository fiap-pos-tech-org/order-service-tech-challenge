# language: pt

Funcionalidade: Produto

  @smoke @high @quick
  Cenário: Criar um novo produto
    Quando preencher todos os dados para cadastro do produto
    Então o produto deve ser criado com sucesso
    E deve exibir o produto cadastrado

  @ignore @high @slow
  Cenário: Buscar um produto
    Dado que um produto já está cadastrado
    Quando realizar a busca do produto
    Então o produto deve ser exibido com sucesso

  @ignore @low
  Cenário: Alterar um produto
    Dado que um produto já está cadastrado
    Quando realizar a requisição para alterar o produto
    Então o produto deve ser alterado com sucesso
    E deve exibir o produto alterado

  @ignore
  Cenário: Remover um produto
    Dado que um produto já está cadastrado
    Quando realizar a requisição para remover o produto
    Então o produto deve ser removido com sucesso

  @ignore
  Cenário: Buscar todos os produtos
    Dado que um produto já está cadastrado
    Quando requisitar a lista de todos os produtos
    Então os produtos devem ser exibidos com sucesso

  @ignore
  Cenário: Buscar um produto por categoria
    Dado que um produto já está cadastrado
    Quando realizar a busca do produto por categoria
    Então o produto deve ser exibido com sucesso

  @ignore
  Cenário: Alterar a imagem de um produto
    Dado que um produto já está cadastrado
    Quando realizar a requisição para alterar a imagem do produto
    Então a imagem do produto deve ser alterada com sucesso
