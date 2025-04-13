package DAOclass;

import Models.Endereco;
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
    private String sqlist = "SELECT u.id, u.nome, u.telefone, u.data_de_nascimento, u.data_de_cadastro, " + "e.Rua, e.cep " + "FROM usuario u " + "JOIN endereco e ON u.id_endereco = e.id";

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
    }

    public ObservableList<Usuario> listarusuario() throws SQLException {
        ObservableList<Usuario> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlist);
        while (rs.next()){
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setDataDeNascimento(rs.getDate("data_de_nascimento").toLocalDate());
            usuario.setDataDeRegistro(rs.getDate("data_de_cadastro").toLocalDate());

            Endereco endereco = new Endereco();
            endereco.setRua(rs.getString("Rua"));
            endereco.setCep(rs.getString("cep"));
            usuario.setEndereco(endereco);
            lista.add(usuario);

        }
        return lista;
    }

}
