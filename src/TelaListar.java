import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TelaListar extends JFrame{
    private JPanel PNLTelaListar;
    private JTable tblTable;
    private JButton btnVoltar;
    final String URL = "jdbc:mysql://localhost:3306/cadcliente";
    final String USER = "root";
    final String PASSWORD = "root";
    final String CONSULTA = "select * from cliente";

    public TelaListar() {
        Conecta();
        AddListeners();
        IniciarComponentes();
    }
    public void Conecta(){
        DefaultTableModel Clientes = new DefaultTableModel();
        Clientes.addColumn("Cod Cliente", new String[]{"Cod Cliente"});
        Clientes.addColumn("Nome", new String[]{"Nome"});
        Clientes.addColumn("Sobrenome", new String[]{"Sobrenome"});
        Clientes.addColumn("Profissão", new String[]{"Profissão"});
        Clientes.addColumn("Telefone", new String[]{"Telefone"});
        tblTable.setModel(Clientes);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = null;
            stmt = connection.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(CONSULTA);
            while (rs.next()){
                Object[] row = new Object[5];
                row[0] =rs.getObject(1);
                row[1] =rs.getObject(2);
                row[2] =rs.getObject(3);
                row[3] =rs.getObject(4);
                row[4] =rs.getObject(5);
                Clientes.addRow(row);
            }
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    public void AddListeners(){
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCadastro telaCadastro = new TelaCadastro();
                telaCadastro.setVisible(true);
                dispose();
            }
        });
    }
    public void IniciarComponentes(){
        JPanel TelaListar = new JPanel();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setContentPane(PNLTelaListar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Relatório de Clientes");
        setVisible(true);
    }
}
