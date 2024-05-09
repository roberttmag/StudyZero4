# Sistema de Vendas Zero4

O **Sistema de Vendas** é uma aplicação Java para gerenciar pedidos de vendas, clientes e produtos. Ele permite adicionar clientes, produtos, criar pedidos e adicionar itens aos pedidos.

## Funcionalidades Principais

- **Cadastro de Clientes:** Adição, edição e remoção de clientes.
- **Cadastro de Produtos:** Adição, edição e remoção de produtos.
- **Gestão de Pedidos:** Criação de novos pedidos, adição de itens aos pedidos e listagem de pedidos.

## Tecnologias Utilizadas

- **Java:** Linguagem de programação principal.
- **Swing:** Biblioteca para criação de interfaces gráficas em Java.
- **MySQL:** Banco de dados relacional para armazenamento de dados.

## Estrutura do Projeto

- **SistVendas:** Pacote principal do projeto.
  - **Cliente:** Classes relacionadas ao cadastro de clientes.
  - **Produto:** Classes relacionadas ao cadastro de produtos.
  - **Vendas:** Classe principal que controla a interface do usuário e a lógica do sistema.
  - **Conexao:** Classe responsável pela conexão com o banco de dados MySQL.

## Como Executar o Projeto

1. Certifique-se de ter o Java Development Kit (JDK) instalado na sua máquina.
2. Configure um banco de dados MySQL local com as tabelas conforme especificado no arquivo de criação do banco de dados.
3. Abra o projeto em sua IDE Java preferida.
4. Compile e execute a classe `Vendas.java` para iniciar a aplicação.

## Contribuindo

Contribuições são bem-vindas! Se você encontrar algum problema, ou deseja adicionar novas funcionalidades, sinta-se à vontade para abrir uma *issue* ou enviar um *pull request*.

## Licença

Este projeto está licenciado sob a [Licença MIT](https://opensource.org/licenses/MIT).
