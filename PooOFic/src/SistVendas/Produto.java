package SistVendas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Produto extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtIdPesquisa;
    private JTextField txtDescricaoPesquisa;
    private String descricao;
    private double preco;

    public Produto() {
    	setTitle("Lista de Carros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panelButtons = new JPanel();
        contentPane.add(panelButtons, BorderLayout.NORTH);

        JButton btnNovo = new JButton("Novo");
        btnNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirCadastroProduto();
            }
        });
        panelButtons.add(btnNovo);

        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pesquisarProdutos();
            }
        });
        panelButtons.add(btnPesquisar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluirProduto();
            }
        });
        panelButtons.add(btnExcluir);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarProdutos();
            }
        });
        panelButtons.add(btnAtualizar);

        JPanel panelPesquisa = new JPanel();
        contentPane.add(panelPesquisa, BorderLayout.SOUTH);

        JLabel lblIdPesquisa = new JLabel("ID:");
        panelPesquisa.add(lblIdPesquisa);

        txtIdPesquisa = new JTextField();
        panelPesquisa.add(txtIdPesquisa);
        txtIdPesquisa.setColumns(10);

        JLabel lblDescricaoPesquisa = new JLabel("Descrição:");
        panelPesquisa.add(lblDescricaoPesquisa);

        txtDescricaoPesquisa = new JTextField();
        panelPesquisa.add(txtDescricaoPesquisa);
        txtDescricaoPesquisa.setColumns(10);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);

        tableModel = new DefaultTableModel();
        table.setModel(tableModel);
        tableModel.addColumn("ID");
        tableModel.addColumn("Descrição");
        tableModel.addColumn("Preço");

        // Adicionar aqui a chamada para listar os produtos ao abrir a tela
        // listarProdutos();
    }

    private void abrirCadastroProduto() {
        CadastroProduto cadastroProduto = new CadastroProduto(this); // Passando a referência de Produto para CadastroProduto
        cadastroProduto.setVisible(true);
    }


    void pesquisarProdutos() {
        // Limpa os dados da tabela antes de preencher com os resultados da pesquisa
        tableModel.setRowCount(0);

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM Produto WHERE 1=1";
            if (!txtIdPesquisa.getText().isEmpty()) {
                sql += " AND Id = ?";
            }
            if (!txtDescricaoPesquisa.getText().isEmpty()) {
                sql += " AND descricao LIKE ?";
            }
            stmt = conn.prepareStatement(sql);
            int index = 1;
            if (!txtIdPesquisa.getText().isEmpty()) {
                stmt.setInt(index++, Integer.parseInt(txtIdPesquisa.getText()));
            }
            if (!txtDescricaoPesquisa.getText().isEmpty()) {
                stmt.setString(index++, "%" + txtDescricaoPesquisa.getText() + "%");
            }
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Id");
                String descricao = rs.getString("descricao");
                double preco = rs.getDouble("preco");

                // Adiciona os dados do produto na tabela
                tableModel.addRow(new Object[]{id, descricao, preco});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar produtos: " + ex.getMessage());
        } finally {
            // Fecha os recursos
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void excluirProduto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idProduto = (int) table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o produto?", "Excluir Produto", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Connection conn = null;
                PreparedStatement stmt = null;
                try {
                    conn = Conexao.conectar();
                    stmt = conn.prepareStatement("DELETE FROM Produto WHERE Id = ?");
                    stmt.setInt(1, idProduto);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Produto excluído com sucesso.");
                    pesquisarProdutos(); // Atualiza a tabela após a exclusão
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao excluir produto: " + ex.getMessage());
                } finally {
                    // Fecha os recursos
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto para excluir.");
        }
    }

    private void atualizarProdutos() {
        pesquisarProdutos(); // Atualiza os produtos exibidos na tabela
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Produto frame = new Produto();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

	public void salvarProduto(Produto produto) {
		// TODO Auto-generated method stub
		
	}

	}

