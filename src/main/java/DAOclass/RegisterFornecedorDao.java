package DAOclass;

import Models.Endereco;
import Models.Fornecedor;
import Models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software.consultoria.DatabaseConnector;

import java.sql.*;
import java.time.LocalDate;

public class RegisterFornecedorDao {
    DatabaseConnector dbConnector = new DatabaseConnector();
    private String sqlEndereco = "INSERT INTO endereco (cidade, Rua, bairro,cep) VALUES (?,?,?,?)";
    private String sqlFornecedor = "INSERT INTO fornecedor (id_endereco, nome, email, telefone, cnpj_cpf, data_de_cadastro) VALUES (?,?,?,?,?,?)";


    public void salvarFornecedor(String nome, String telefone, String email, String CNPJ, String cep, String Rua, String Cidade, String Bairro, LocalDate DataReg) throws SQLException {

        Connection conn = dbConnector.connect();
        PreparedStatement pstmtEndereco = conn.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS);
        pstmtEndereco.setString(1,Cidade);
        pstmtEndereco.setString(2,Rua);
        pstmtEndereco.setString(3,Bairro);
        pstmtEndereco.setString(4,cep);
        pstmtEndereco.executeUpdate();

        ResultSet rsEndereco = pstmtEndereco.getGeneratedKeys();
        int idEndereco = -1;
        if(rsEndereco.next()){
            idEndereco = rsEndereco.getInt(1);
        }
        PreparedStatement pstmtFornecedor = conn.prepareStatement(sqlFornecedor, Statement.RETURN_GENERATED_KEYS);
        pstmtFornecedor.setInt(1,idEndereco);
        pstmtFornecedor.setString(2,nome);
        pstmtFornecedor.setString(3,email);
        pstmtFornecedor.setString(4,telefone);
        pstmtFornecedor.setString(5,CNPJ);
        pstmtFornecedor.setDate(6, Date.valueOf(DataReg));
        pstmtFornecedor.executeUpdate();

        pstmtEndereco.close();
        pstmtFornecedor.close();
        conn.close();

    }
}
