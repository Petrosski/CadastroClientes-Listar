import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TelaCadastro extends JFrame{
    private JTextField txtNome;
    private JTextField txtSobrenome;
    private JTextField txtProfisao;
    private JTextField txtTelefone;
    private JButton btnCadastar;
    private JButton btnListar;
    private JPanel PNLTelaCadastro;
    final String URL = "jdbc:mysql://localhost:3306/cadcliente";
    final String USER = "root";
    final String PASSWORD = "root";
    final String INSERIR = "INSERT INTO cliente (nome, sobrenome, profissao, telefone) VALUES (?, ?, ?, ?)";
    public TelaCadastro() {
        Conecta();
        AddListeners();
        IniciarComponentes();
    }
    public void Conecta() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado");
            final PreparedStatement stmtInserir = connection.prepareStatement(INSERIR);

            btnCadastar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nome = txtNome.getText();
                    String sobrenome = txtSobrenome.getText();
                    String profissao = txtProfisao.getText();
                    String telefone = txtTelefone.getText();
                    try {
                        stmtInserir.setString(1,nome);
                        stmtInserir.setString(2,sobrenome);
                        stmtInserir.setString(3,profissao);
                        stmtInserir.setString(4,telefone);
                        int result = stmtInserir.executeUpdate();
                        if (result > 0){
                            String mensagem = "Nome: "+nome+ "\nSobrenome: "+sobrenome+"\nProfissão: "+profissao+"\nTelefone: "+telefone;
                            JOptionPane.showMessageDialog(null,mensagem,"Cliente cadastrado com sucesso",JOptionPane.PLAIN_MESSAGE);
                            txtNome.setText("");
                            txtSobrenome.setText("");
                            txtProfisao.setText("");
                            txtTelefone.setText("");
                        }
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null,"Erro ao inserir dados: "+ex.getMessage());
                    }
                }
            });

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Erro ao conectar BD: "+ex.getMessage());
        }
    }
    public void AddListeners(){
        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaListar telaListar = new TelaListar();
                telaListar.setVisible(true);
                dispose();
            }
        });
    }
    public void IniciarComponentes(){
        JPanel TelaCadastro = new JPanel();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setContentPane(PNLTelaCadastro);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cadastro de Clientes");
        setVisible(true);
    }
}
