package SistVendas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;

public class Principal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Principal frame = new Principal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Principal() {
    	setTitle("Zero4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNewButton = new JButton("Cliente");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = new Cliente();
                cliente.setVisible(true);
            }
        });
        btnNewButton.setBounds(21, 78, 128, 37);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Produto");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Produto produto = new Produto();
                produto.setVisible(true);
            }
        });
        btnNewButton_1.setBounds(159, 78, 128, 37);
        contentPane.add(btnNewButton_1);
        JButton btnNewButton_2 = new JButton("Vendas");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             
                Vendas vendas = new Vendas();
                vendas.setVisible(true);
            }
        });
        btnNewButton_2.setBounds(293, 78, 133, 37);
        contentPane.add(btnNewButton_2);
        
        JLabel lblNewLabel = new JLabel("Zero4 Car");
        lblNewLabel.setForeground(new Color(255, 0, 0));
        lblNewLabel.setFont(new Font("Barbershop in Thailand", Font.BOLD, 33));
        lblNewLabel.setBounds(145, 27, 159, 41);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Seu Carro,  Seu Sonho!");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Poppins Black", Font.PLAIN, 16));
        lblNewLabel_1.setBounds(123, 172, 206, 37);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Venha Comprar seu Veiculo com a gente!");
        lblNewLabel_2.setFont(new Font("Poppins", Font.BOLD, 10));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(97, 203, 259, 13);
        contentPane.add(lblNewLabel_2);
    }
}
