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
    private String sqlist = "SELECT u.id, u.nome, u.email, u.telefone, u.cnpj_cpf, u.data_de_cadastro, e.Rua, e.cep " + "FROM fornecedor u " + "JOIN endereco e ON u.id_endereco = e.id";


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
    public ObservableList<Fornecedor> listarFornecedor() throws SQLException {
        ObservableList<Fornecedor> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlist);
        while (rs.next()){
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(rs.getInt("id"));
            fornecedor.setNome(rs.getString("nome"));
            fornecedor.setEmail(rs.getString("email"));
            fornecedor.setTelefone(rs.getString("telefone"));
            fornecedor.setCnpj(rs.getString("cnpj_cpf"));
            fornecedor.setDataDeCadastro(rs.getDate("data_de_cadastro").toLocalDate());

            Endereco endereco = new Endereco();
            endereco.setRua(rs.getString("Rua"));
            endereco.setCep(rs.getString("cep"));
            fornecedor.setEndereco(endereco);
            lista.add(fornecedor);

        }
        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }

}
