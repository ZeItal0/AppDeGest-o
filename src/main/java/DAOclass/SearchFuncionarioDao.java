package DAOclass;

import Models.Endereco;
import Models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software.consultoria.DatabaseConnector;

import java.sql.*;

public class SearchFuncionarioDao {

    DatabaseConnector dbConnector = new DatabaseConnector();

    private String sqlist = "SELECT u.id, u.nome, u.telefone, u.data_de_nascimento, u.data_de_cadastro, " + "e.Rua, e.cep " + "FROM usuario u " + "JOIN endereco e ON u.id_endereco = e.id " + "ORDER BY u.data_de_cadastro DESC " +
            "LIMIT 100;";
    String sqlBusca = """
                SELECT u.id, u.nome, u.telefone, u.data_de_nascimento, u.data_de_cadastro,
                       e.Rua, e.cep
                FROM usuario u
                JOIN endereco e ON u.id_endereco = e.id
                WHERE u.nome LIKE ?
                ORDER BY u.data_de_cadastro DESC
                LIMIT 100;
            """;


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
        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }

    public ObservableList<Usuario> buscaPorNome(String nome) throws SQLException {
        ObservableList<Usuario> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sqlBusca);
        pstmt.setString(1,"%"+nome+"%");
        ResultSet rs = pstmt.executeQuery();

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
        rs.close();
        pstmt.close();
        conn.close();
        return lista;
    }
}
