package DAOclass;

import Models.Endereco;
import Models.Fornecedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software.consultoria.DatabaseConnector;

import java.sql.*;

public class SearchFornecedorDao {
    DatabaseConnector dbConnector = new DatabaseConnector();

    private String sqlist = "SELECT u.id, u.nome, u.email, u.telefone, u.cnpj_cpf, u.data_de_cadastro, e.Rua, e.cep, e.cidade, e.bairro " + "FROM fornecedor u " + "JOIN endereco e ON u.id_endereco = e.id "+"ORDER BY \n" +
            "    u.data_de_cadastro DESC\n" +
            "LIMIT 100;";
    private String sqBusca = "SELECT u.id, u.nome, u.email, u.telefone, u.cnpj_cpf, u.data_de_cadastro, " + "e.Rua, e.cep, e.cidade, e.bairro " + "FROM fornecedor u " + "JOIN endereco e ON u.id_endereco = e.id " + "WHERE u.nome LIKE ? OR u.email LIKE ? OR u.telefone LIKE ? OR u.cnpj_cpf LIKE ? OR e.Rua LIKE ? OR e.cep LIKE ? OR e.cidade LIKE ? OR e.bairro LIKE ? "+"ORDER BY \n" +
            "    u.data_de_cadastro DESC\n" +
            "LIMIT 100";
    private String sqlCnpj = "SELECT COUNT(*) FROM fornecedor WHERE cnpj_cpf = ?";

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
            endereco.setCidade(rs.getString("cidade"));
            endereco.setBairro(rs.getString("bairro"));
            fornecedor.setEndereco(endereco);
            lista.add(fornecedor);

        }
        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }
    public ObservableList<Fornecedor> BuscarFornecedor(String nome) throws SQLException {
        ObservableList<Fornecedor> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sqBusca);
        pstmt.setString(1,"%"+nome+"%");
        pstmt.setString(2,"%"+nome+"%");
        pstmt.setString(3,"%"+nome+"%");
        pstmt.setString(4,"%"+nome+"%");
        pstmt.setString(5,"%"+nome+"%");
        pstmt.setString(6,"%"+nome+"%");
        pstmt.setString(7,"%"+nome+"%");
        pstmt.setString(8,"%"+nome+"%");
        ResultSet rs = pstmt.executeQuery();
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
            endereco.setCidade(rs.getString("cidade"));
            endereco.setBairro(rs.getString("bairro"));
            fornecedor.setEndereco(endereco);
            lista.add(fornecedor);

        }
        rs.close();
        pstmt.close();
        conn.close();
        return lista;
    }
    public boolean Verificarcnpj(String cnpj) throws SQLException {
        Connection conn = dbConnector.connect();

        PreparedStatement pstmtVerificar = conn.prepareStatement(sqlCnpj);
        pstmtVerificar.setString(1,cnpj);
        ResultSet rs = pstmtVerificar.executeQuery();

        boolean ext = false;
        if (rs.next()){
            ext = rs.getInt(1) > 0;
        }
        rs.close();
        pstmtVerificar.close();
        conn.close();
        return ext;

    }
}
