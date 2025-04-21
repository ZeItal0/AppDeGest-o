package DAOclass;

import Models.Endereco;
import Models.Sessao;
import Models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software.consultoria.DatabaseConnector;


import java.sql.*;
import java.time.LocalDate;

public class RegisterUsuarioDao {

    DatabaseConnector dbConnector = new DatabaseConnector();
    private String sqlEndereco = "INSERT INTO endereco (cidade, Rua, bairro,cep) VALUES (?,?,?,?)";
    private String sqlUsario = "INSERT INTO usuario (id_endereco, nome, senha, loginUser, telefone, data_de_nascimento, data_de_cadastro) VALUES (?,?,?,?,?,?,?)";
    private String sqlFuncionario = "INSERT INTO funcionario (id_usuario, cargo) VALUES (?,?)";
    private String sqllogin = "SELECT id, nome FROM usuario WHERE loginUser = ? AND senha = ?";

    public void salvarusuario(String nome, String telefone, String cep, String rua, String Cidade, String bairro, String User, String senha, String buttonMenu, LocalDate DataNas, LocalDate DataReg) throws SQLException {

        Connection conn = dbConnector.connect();
        PreparedStatement pstmtEndereco = conn.prepareStatement(sqlEndereco,Statement.RETURN_GENERATED_KEYS);
        pstmtEndereco.setString(1,Cidade);
        pstmtEndereco.setString(2,rua);
        pstmtEndereco.setString(3,bairro);
        pstmtEndereco.setString(4,cep);
        pstmtEndereco.executeUpdate();

        ResultSet rsEndereco = pstmtEndereco.getGeneratedKeys();
        int idEndereco = -1;
        if(rsEndereco.next()){
            idEndereco = rsEndereco.getInt(1);
        }

        PreparedStatement pstmtUsuario = conn.prepareStatement(sqlUsario, Statement.RETURN_GENERATED_KEYS);
        pstmtUsuario.setInt(1,idEndereco);
        pstmtUsuario.setString(2,nome);
        pstmtUsuario.setString(3,senha);
        pstmtUsuario.setString(4,User);
        pstmtUsuario.setString(5,telefone);
        pstmtUsuario.setDate(6, Date.valueOf(DataNas));
        pstmtUsuario.setDate(7, Date.valueOf(DataReg));
        pstmtUsuario.executeUpdate();

        ResultSet rsUsuario = pstmtUsuario.getGeneratedKeys();
        int idUsuario = -1;
        if (rsUsuario.next()){
            idUsuario = rsUsuario.getInt(1);
        }

        PreparedStatement pstmtFuncionario = conn.prepareStatement(sqlFuncionario);
        pstmtFuncionario.setInt(1,idUsuario);
        pstmtFuncionario.setString(2,buttonMenu);
        pstmtFuncionario.executeUpdate();

        pstmtEndereco.close();
        pstmtUsuario.close();
        pstmtFuncionario.close();
        conn.close();
    }

    public boolean verificarlogin (String login, String senha) throws SQLException {

        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement stmtlogin = null;

        try {
            conn = dbConnector.connect();
            stmtlogin = conn.prepareStatement(sqllogin);
            stmtlogin.setString(1,login);
            stmtlogin.setString(2,senha);
            rs = stmtlogin.executeQuery();

            if (rs.next()){
                Sessao.nome = rs.getString("nome");
                Sessao.id = rs.getInt("id");
                return true;
            }
            else {
                return false;
            }

        }finally {
            if (rs != null) rs.close();
            if (stmtlogin != null) stmtlogin.close();
            if (conn != null) conn.close();

        }

    }

}
