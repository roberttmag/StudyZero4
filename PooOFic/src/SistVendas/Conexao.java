package SistVendas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexao {

    public static void salvarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nome, dtCadastro) VALUES (?, NOW())";

        try (
            Connection conn = conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, cliente.getName());

            stmt.executeUpdate();
            System.out.println("Cliente salvo com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    public static void salvarProduto(Produto produto) {
        String sql = "INSERT INTO Produto (descricao, preco) VALUES (?, ?)";

        try (
            Connection conn = conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getPreco());

            stmt.executeUpdate();
            System.out.println("Produto salvo com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao salvar produto: " + e.getMessage());
        }
    }


    public static Connection conectar() {
        String servidor = "jdbc:mysql://localhost:3306/VendasPOO";
        String usuario = "root";
        String senha = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(servidor, usuario, senha);
            System.out.println("Conexão bem-sucedida com o banco de dados.");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }

        return connection;
    }
}
