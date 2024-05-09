package SistVendas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Cliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;

    public Cliente() {
    	setTitle("Lista de Clientes");
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
                abrirCadastroCliente();
            }
        });
        panelButtons.add(btnNovo);

        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pesquisarClientes();
            }
        });
        panelButtons.add(btnPesquisar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluirCliente();
            }
        });
        panelButtons.add(btnExcluir);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarClientes();
            }
        });
        panelButtons.add(btnAtualizar);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);

        tableModel = new DefaultTableModel();
        table.setModel(tableModel);
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Data Cadastro");
    }

    private void abrirCadastroCliente() {
        // Aqui você pode adicionar o código para abrir a tela de cadastro de cliente
        CadastroCliente cadastroCliente = new CadastroCliente();
        cadastroCliente.setVisible(true);
    }

    private void pesquisarClientes() {
        // Limpa os dados da tabela antes de preencher com os resultados da pesquisa
        tableModel.setRowCount(0);

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            stmt = conn.prepareStatement("SELECT * FROM Cliente");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Id");
                String nome = rs.getString("nome");
                Date dtCadastro = rs.getDate("dtCadastro");

                // Adiciona os dados do cliente na tabela
                tableModel.addRow(new Object[]{id, nome, dtCadastro});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar clientes: " + ex.getMessage());
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

    private void excluirCliente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idCliente = (int) table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o cliente?", "Excluir Cliente", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Connection conn = null;
                PreparedStatement stmt = null;
                try {
                    conn = Conexao.conectar();
                    stmt = conn.prepareStatement("DELETE FROM Cliente WHERE Id = ?");
                    stmt.setInt(1, idCliente);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso.");
                    pesquisarClientes(); // Atualiza a tabela após a exclusão
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao excluir cliente: " + ex.getMessage());
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
            JOptionPane.showMessageDialog(null, "Selecione um cliente para excluir.");
        }
    }

    private void atualizarClientes() {
        pesquisarClientes(); // Atualiza os clientes exibidos na tabela
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cliente frame = new Cliente();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
