package SistVendas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroCliente extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNome;

    public CadastroCliente() {
        setTitle("Cadastro de Cliente");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel();
        getContentPane().add(panelCampos, BorderLayout.CENTER);
        panelCampos.setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Poppins", Font.BOLD, 17));
        lblNome.setBounds(22, 21, 143, 50);
        panelCampos.add(lblNome);

        textFieldNome = new JTextField();
        textFieldNome.setFont(new Font("Poppins", Font.BOLD, 15));
        textFieldNome.setBounds(92, 22, 184, 50);
        panelCampos.add(textFieldNome);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomeCliente = textFieldNome.getText();

                if (!nomeCliente.isEmpty()) {
                    Cliente cliente = new Cliente();
                    cliente.setName(nomeCliente);
                    Conexao.salvarCliente(cliente);

                    dispose(); // 
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, insira o nome do cliente.");
                }
            }
        });
        getContentPane().add(btnSalvar, BorderLayout.SOUTH);
    }
}
