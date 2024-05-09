package SistVendas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Vendas extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JComboBox<String> comboBoxCliente;
    private JComboBox<String> comboBoxProduto;
    private JTextField txtQuantidade;
    private JTable tablePedidos;
    private DefaultTableModel tableModelPedidos;

    public Vendas() {
    	setTitle("Zero4 Lista de Pedidos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panelTop = new JPanel();
        panelTop.setBounds(5, 5, 776, 31);
        contentPane.add(panelTop);

        JLabel lblCliente = new JLabel("Cliente:");
        panelTop.add(lblCliente);

        comboBoxCliente = new JComboBox<>();
        carregarClientes();
        panelTop.add(comboBoxCliente);

        JLabel lblProduto = new JLabel("Produto:");
        panelTop.add(lblProduto);

        comboBoxProduto = new JComboBox<>();
        carregarProdutos();
        panelTop.add(comboBoxProduto);

        JLabel lblQuantidade = new JLabel("Quantidade:");
        panelTop.add(lblQuantidade);

        txtQuantidade = new JTextField();
        panelTop.add(txtQuantidade);
        txtQuantidade.setColumns(5);

        JButton btnAdicionarItem = new JButton("Adicionar Item");
        btnAdicionarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarItem();
            }
        });
        panelTop.add(btnAdicionarItem);

        JButton btnListarPedidos = new JButton("Listar Pedidos");
        btnListarPedidos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listarPedidos();
            }
        });
        panelTop.add(btnListarPedidos);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(5, 36, 776, 297);
        contentPane.add(scrollPane);

        tablePedidos = new JTable();
        scrollPane.setViewportView(tablePedidos);

        tableModelPedidos = new DefaultTableModel();
        tablePedidos.setModel(tableModelPedidos);
        
        JLabel lblNewLabel = new JLabel("Zero4 Car");
        lblNewLabel.setBackground(new Color(240, 240, 240));
        lblNewLabel.setForeground(new Color(255, 0, 0));
        lblNewLabel.setFont(new Font("Barbershop in Thailand", Font.BOLD, 59));
        lblNewLabel.setBounds(252, 400, 292, 135);
        contentPane.add(lblNewLabel);
        tableModelPedidos.addColumn("Data");
        tableModelPedidos.addColumn("Cliente");
        tableModelPedidos.addColumn("Produto");
        tableModelPedidos.addColumn("Preço");
        tableModelPedidos.addColumn("Quantidade");
        tableModelPedidos.addColumn("Total");
    }

    private void carregarClientes() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT nome FROM Cliente";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            comboBoxCliente.removeAllItems(); // Limpa os itens existentes no comboBox
            
            while (rs.next()) {
                String nomeCliente = rs.getString("nome");
                comboBoxCliente.addItem(nomeCliente); // Adiciona o nome do cliente ao comboBox
            }
            
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarProdutos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT descricao FROM Produto";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            comboBoxProduto.removeAllItems(); // Limpa os itens existentes no comboBox
            
            while (rs.next()) {
                String descricaoProduto = rs.getString("descricao");
                comboBoxProduto.addItem(descricaoProduto); // Adiciona a descrição do produto ao comboBox
            }
            
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar produtos: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void adicionarItem() {
        try {
            String clienteSelecionado = (String) comboBoxCliente.getSelectedItem();
            String produtoSelecionado = (String) comboBoxProduto.getSelectedItem();
            int quantidade = Integer.parseInt(txtQuantidade.getText());

            if (clienteSelecionado == null || produtoSelecionado == null || quantidade <= 0) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente.");
                return;
            }

            Connection conn = Conexao.conectar();
            conn.setAutoCommit(false);

            int clienteId = obterClienteId(clienteSelecionado, conn);

            double preco = 0;
            int produtoId = 0;
            PreparedStatement stmtProduto = conn.prepareStatement("SELECT Id, preco FROM Produto WHERE descricao = ?");
            stmtProduto.setString(1, produtoSelecionado);
            ResultSet rsProduto = stmtProduto.executeQuery();
            if (rsProduto.next()) {
                produtoId = rsProduto.getInt("Id");
                preco = rsProduto.getDouble("preco");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
                conn.rollback();
                conn.close();
                return;
            }

            double total = preco * quantidade;

            PreparedStatement stmtPedido = conn.prepareStatement("INSERT INTO Pedido (dtCadastro, ClienteId) VALUES (NOW(), ?)", Statement.RETURN_GENERATED_KEYS);
            stmtPedido.setInt(1, clienteId);
            stmtPedido.executeUpdate();

            ResultSet rsPedido = stmtPedido.getGeneratedKeys();
            int pedidoId = 0;
            if (rsPedido.next()) {
                pedidoId = rsPedido.getInt(1);
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao obter ID do pedido.");
                conn.rollback();
                conn.close();
                return;
            }

            PreparedStatement stmtItem = conn.prepareStatement("INSERT INTO Item (PedidoId, ProdutoId, Preco, Quantidade) VALUES (?, ?, ?, ?)");
            stmtItem.setInt(1, pedidoId);
            stmtItem.setInt(2, produtoId);
            stmtItem.setDouble(3, preco);
            stmtItem.setInt(4, quantidade);
            stmtItem.executeUpdate();

            conn.commit();
            conn.close();

            JOptionPane.showMessageDialog(null, "Item adicionado ao pedido com sucesso.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar item ao pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int obterClienteId(String nomeCliente, Connection conn) throws SQLException {
        int clienteId = 0;
        PreparedStatement stmtCliente = conn.prepareStatement("SELECT Id FROM Cliente WHERE nome = ?");
        stmtCliente.setString(1, nomeCliente);
        ResultSet rsCliente = stmtCliente.executeQuery();
        if (rsCliente.next()) {
            clienteId = rsCliente.getInt("Id");
        }
        return clienteId;
    }

    private void listarPedidos() {
        try {
            LocalDate dataAtual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dataFormatada = dataAtual.format(formatter);
            
            Connection conn = Conexao.conectar();
            String sql = "SELECT p.dtCadastro, c.nome AS cliente, pr.descricao AS produto, i.preco, i.quantidade, (i.preco * i.quantidade) AS total " +
                         "FROM Pedido p " +
                         "JOIN Cliente c ON p.ClienteId = c.Id " +
                         "JOIN Item i ON p.Id = i.PedidoId " +
                         "JOIN Produto pr ON i.ProdutoId = pr.Id " +
                         "WHERE DATE(p.dtCadastro) = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dataFormatada);
            ResultSet rs = stmt.executeQuery();
            
            // Limpa as linhas existentes na tabela
            tableModelPedidos.setRowCount(0);
            
            while (rs.next()) {
                String dataPedido = rs.getString("dtCadastro");
                String cliente = rs.getString("cliente");
                String produto = rs.getString("produto");
                double preco = rs.getDouble("preco");
                int quantidade = rs.getInt("quantidade");
                double total = rs.getDouble("total");
                
                // Adiciona os dados do pedido à tabela
                tableModelPedidos.addRow(new Object[]{dataPedido, cliente, produto, preco, quantidade, total});
            }
            
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar pedidos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Vendas frame = new Vendas();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
