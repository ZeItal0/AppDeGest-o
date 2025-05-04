package DAOclass;

import Models.Usuario;
import software.consultoria.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateUsuarioDao {
    DatabaseConnector dbConnector = new DatabaseConnector();
    String sqlUpdateUsuario = "UPDATE usuario SET nome = ?, telefone = ? WHERE id = ?";
    String sqlUpdateEndereco = "UPDATE endereco SET cep = ?, Rua = ?, cidade = ?, bairro = ? WHERE id = (SELECT id_endereco FROM usuario WHERE id = ?)";

    public void atualizarFuncionario(int id, String nome, String telefone, String cep, String rua,String cidade,String bairro) throws SQLException {
        Connection conn = dbConnector.connect();
        PreparedStatement pstmtUsuario = conn.prepareStatement(sqlUpdateUsuario);
        pstmtUsuario.setString(1,nome);
        pstmtUsuario.setString(2,telefone);
        pstmtUsuario.setInt(3,id);
        pstmtUsuario.executeUpdate();

        PreparedStatement pstmtEndereco = conn.prepareStatement(sqlUpdateEndereco);
        pstmtEndereco.setString(1,cep);
        pstmtEndereco.setString(2,rua);
        pstmtEndereco.setString(3,cidade);
        pstmtEndereco.setString(4,bairro);
        pstmtEndereco.setInt(5,id);
        pstmtEndereco.executeUpdate();

        pstmtUsuario.close();
        pstmtEndereco.close();
        conn.close();
    }
}
