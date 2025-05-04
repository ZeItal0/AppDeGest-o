package DAOclass;

import software.consultoria.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateFornecedorDao {
    DatabaseConnector dbConnector = new DatabaseConnector();
    private String sqlUpdateFornecedor = "UPDATE fornecedor SET nome = ?, email = ?, telefone = ?, cnpj_cpf = ? WHERE id = ?";
    private String sqlUpdateEndereco = "UPDATE endereco SET Rua = ?, cidade = ?, bairro = ?, cep = ? WHERE id = (SELECT id_endereco FROM fornecedor WHERE id = ?)";

    public void atualizarFornecedor(int id, String nome, String telefone, String cnpj, String cep, String rua, String email,String cidade,String bairro) throws SQLException {
        Connection conn = dbConnector.connect();

        PreparedStatement pstmtFornecedor = conn.prepareStatement(sqlUpdateFornecedor);
        pstmtFornecedor.setString(1,nome);
        pstmtFornecedor.setString(2,email);
        pstmtFornecedor.setString(3,telefone);
        pstmtFornecedor.setString(4,cnpj);
        pstmtFornecedor.setInt(5,id);
        pstmtFornecedor.executeUpdate();

        PreparedStatement pstmtEndereco = conn.prepareStatement(sqlUpdateEndereco);
        pstmtEndereco.setString(1,rua);
        pstmtEndereco.setString(2,cidade);
        pstmtEndereco.setString(3,bairro);
        pstmtEndereco.setString(4,cep);
        pstmtEndereco.setInt(5,id);
        pstmtEndereco.executeUpdate();

        pstmtFornecedor.close();
        pstmtEndereco.close();
        conn.close();


    }
}
