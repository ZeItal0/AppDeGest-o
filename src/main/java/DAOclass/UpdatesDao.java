package DAOclass;

import software.consultoria.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdatesDao {
    private final DatabaseConnector dbConnector = new DatabaseConnector();
    public void atualizarQuantidade(int idProduto, int novaQuantidade) {
        try (Connection conn = dbConnector.connect();
             PreparedStatement psmtEstoque = conn.prepareStatement("UPDATE estoque SET quantidade = quantidade - ? WHERE id_produto = ?")) {
            ;
            psmtEstoque.setInt(1, novaQuantidade);
            psmtEstoque.setInt(2, idProduto);
            psmtEstoque.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
