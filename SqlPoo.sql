create database VendasPOO;
use VendasPOO;

CREATE TABLE Cliente (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    dtCadastro DATE NOT NULL
);


CREATE TABLE Produto (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) ,
    preco DECIMAL(10, 2) not null 
);

CREATE TABLE Pedido (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    dtCadastro DATE NOT NULL,
    ClienteId INT,
    FOREIGN KEY (ClienteId) REFERENCES Cliente(Id)
);

CREATE TABLE Item (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    PedidoId INT,
    ProdutoId INT,
    Preco DECIMAL(10, 2) NOT NULL,
    Quantidade INT NOT NULL,
    FOREIGN KEY (PedidoId) REFERENCES Pedido(Id),
    FOREIGN KEY (ProdutoId) REFERENCES Produto(Id)
);

select * from Cliente;
select * from Produto;
select * from pedido;
select * from item;
