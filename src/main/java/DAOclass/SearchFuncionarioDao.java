package DAOclass;

import Models.Endereco;
import Models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software.consultoria.DatabaseConnector;

import java.sql.*;

public class SearchFuncionarioDao {

    DatabaseConnector dbConnector = new DatabaseConnector();

    private String sqlist = """
                SELECT u.id AS id_usuario, u.nome, u.telefone, u.data_de_nascimento, u.data_de_cadastro,
                       e.id AS id_endereco, e.Rua, e.cep, e.cidade, e.bairro
                FROM usuario u
                JOIN endereco e ON u.id_endereco = e.id
                ORDER BY u.data_de_cadastro DESC
                LIMIT 100;
            """;
    private String sqlBusca = """
                SELECT u.id AS id_usuario, u.nome, u.telefone, u.data_de_nascimento, u.data_de_cadastro,
                        e.id AS id_endereco, e.Rua, e.cep, e.cidade, e.bairro
                FROM usuario u
                JOIN endereco e ON u.id_endereco = e.id
                WHERE u.nome LIKE ? OR u.telefone LIKE ? OR e.Rua LIKE ? OR e.cep LIKE ? OR e.cidade LIKE ? OR e.bairro LIKE ?
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
            usuario.setId(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setDataDeNascimento(rs.getDate("data_de_nascimento").toLocalDate());
            usuario.setDataDeRegistro(rs.getDate("data_de_cadastro").toLocalDate());

            Endereco endereco = new Endereco();
            endereco.setId(rs.getInt("id_endereco"));
            endereco.setRua(rs.getString("Rua"));
            endereco.setCep(rs.getString("cep"));
            endereco.setCidade(rs.getString("cidade"));
            endereco.setBairro(rs.getString("bairro"));
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
        pstmt.setString(2,"%"+nome+"%");
        pstmt.setString(3,"%"+nome+"%");
        pstmt.setString(4,"%"+nome+"%");
        pstmt.setString(5,"%"+nome+"%");
        pstmt.setString(6,"%"+nome+"%");
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()){
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setDataDeNascimento(rs.getDate("data_de_nascimento").toLocalDate());
            usuario.setDataDeRegistro(rs.getDate("data_de_cadastro").toLocalDate());

            Endereco endereco = new Endereco();
            endereco.setId(rs.getInt("id_endereco"));
            endereco.setRua(rs.getString("Rua"));
            endereco.setCep(rs.getString("cep"));
            endereco.setCidade(rs.getString("cidade"));
            endereco.setBairro(rs.getString("bairro"));
            usuario.setEndereco(endereco);
            lista.add(usuario);

        }
        rs.close();
        pstmt.close();
        conn.close();
        return lista;
    }
}
