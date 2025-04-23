package DAOclass;

import Models.Categoria;
import software.consultoria.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateProdutosDao {
    DatabaseConnector dbConnector = new DatabaseConnector();
    private String sqlUpdateproduto = "UPDATE produto SET nome_produto = ?, preco = ?, preco_de_venda = ?, detalhes_produto = ? WHERE id = ? ";
    private String sqlUpdateEstoque = "UPDATE estoque set quantidade = ? WHERE id_produto = ?";
    private String sqlUpdateCategoria = "UPDATE categoria SET tipo_produto = ? WHERE id =?";

    public void atualizarProdduto(int id, Categoria categoria, double preco, double precoDeVenda, int quantidade, String detalhes, String nomeProduto) throws SQLException {
        Connection conn = dbConnector.connect();

        PreparedStatement pstmtCategoria = conn.prepareStatement(sqlUpdateCategoria);
        pstmtCategoria.setString(1,categoria.getTipo_produto());
        pstmtCategoria.setInt(2,categoria.getId());
        pstmtCategoria.executeUpdate();

        PreparedStatement pstmtProduto = conn.prepareStatement(sqlUpdateproduto);
        pstmtProduto.setString(1,nomeProduto);
        pstmtProduto.setDouble(2,preco);
        pstmtProduto.setDouble(3,precoDeVenda);
        pstmtProduto.setString(4,detalhes);
        pstmtProduto.setInt(5,id);
        pstmtProduto.executeUpdate();

        PreparedStatement pstmtEstoque = conn.prepareStatement(sqlUpdateEstoque);
        pstmtEstoque.setInt(1,quantidade);
        pstmtEstoque.setInt(2,id);
        pstmtEstoque.executeUpdate();

        pstmtCategoria.close();
        pstmtProduto.close();
        pstmtEstoque.close();
        conn.close();

    }
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
