package SistVendas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroProduto extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textFieldDescricao;
    private JTextField textFieldPreco;
    public CadastroProduto(Produto parent) {
        setTitle("Cadastro de Produto");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel();
        getContentPane().add(panelCampos, BorderLayout.CENTER);
        panelCampos.setLayout(null);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setFont(new Font("Poppins", Font.BOLD, 17));
        lblDescricao.setBounds(22, 21, 143, 20);
        panelCampos.add(lblDescricao);

        textFieldDescricao = new JTextField();
        textFieldDescricao.setFont(new Font("Poppins", Font.BOLD, 15));
        textFieldDescricao.setBounds(22, 41, 254, 30);
        panelCampos.add(textFieldDescricao);

        JLabel lblPreco = new JLabel("Preço:");
        lblPreco.setFont(new Font("Poppins", Font.BOLD, 17));
        lblPreco.setBounds(22, 81, 143, 20);
        panelCampos.add(lblPreco);

        textFieldPreco = new JTextField();
        textFieldPreco.setFont(new Font("Poppins", Font.BOLD, 15));
        textFieldPreco.setBounds(22, 101, 254, 30);
        panelCampos.add(textFieldPreco);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String descricao = textFieldDescricao.getText();
                String precoText = textFieldPreco.getText();

                if (!descricao.isEmpty() && !precoText.isEmpty()) {
                    try {
                        double preco = Double.parseDouble(precoText);
                        Produto produto = new Produto();
                        produto.setDescricao(descricao);
                        produto.setPreco(preco);
                        Conexao.salvarProduto(produto); // Chamada para salvar o produto
                        dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Formato de preço inválido.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                }
            }
        });
        getContentPane().add(btnSalvar, BorderLayout.SOUTH);
    }
}
