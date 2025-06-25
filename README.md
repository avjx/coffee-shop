# Coffee Shop - Sistema CRUD Java Swing com MongoDB

## Descrição do Projeto

Coffee Shop é uma aplicação desktop desenvolvida em Java utilizando Swing para interface gráfica e MongoDB como banco de dados.  
O sistema permite o cadastro, edição, exclusão e listagem de itens com as seguintes informações:  
- Código do item (ID)  
- Nome  
- Quantidade  
- Valor  
- Foto (URL ou caminho para a imagem)

O projeto aplica o padrão Singleton para o controlador de dados e utiliza o driver oficial MongoDB Java para persistência.

---

## Funcionalidades

- **Adicionar Item:** Permite inserir um novo item com código único, nome, quantidade, valor e foto.
- **Listar Itens:** Exibe todos os itens cadastrados em uma tabela com suporte a ordenação clicando no cabeçalho.
- **Editar Item:** Permite alterar todos os dados do item, exceto o código (ID), que é imutável.
- **Excluir Item:** Remove o item selecionado do banco.
- **Exibir Foto:** Ao selecionar um item na tabela, sua foto é exibida ao lado para visualização.
- **Validação de IDs:** O sistema impede a inserção de IDs duplicados.
- **Interface Intuitiva:** Layout com painel de itens, tabela, botões e campos organizados.

---

## Banco de Dados - MongoDB

- Banco: `coffee_shop_db`
- Coleção: `itens`
- Documentos com a estrutura:

```json
{
  "id_item": "string",        //" Código único do item (ex: "A123")
  "nome": "string",           //" Nome do produto (ex: "Café Expresso")
  "quantidade": int,          //" Quantidade em estoque (ex: 20)
  "valor": double,            //" Preço do item (ex: 12.50)
  "foto": "string"            //" URL ou caminho relativo da foto (ex: "images/cafe.png")
}
