package DAOclass;

import Models.Categoria;
import Models.Estoque;
import Models.Fornecedor;
import Models.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software.consultoria.DatabaseConnector;

import java.sql.*;

public class SearchProdutoDao {
    DatabaseConnector dbConnector = new DatabaseConnector();

    private String sqlProdutoslist = """
    SELECT 
        p.id AS id_produto,
        p.nome_produto,
        p.preco,
        p.Preco_de_Venda,
        p.detalhes_produto,
        p.data_de_cadastro,
        c.id AS id_categoria,
        c.tipo_produto,
        f.nome AS nome_fornecedor,
        e.quantidade
    FROM produto p
    JOIN categoria c ON p.id_categoria = c.id
    JOIN produto_fornecedor pf ON p.id = pf.id_produto
    JOIN fornecedor f ON pf.id_fornecedor = f.id
    JOIN estoque e ON p.id = e.id_produto
    ORDER BY p.data_de_cadastro DESC
    LIMIT 100;
""";

    private String sqlBusca = """
    SELECT 
        p.id AS id_produto,
        p.nome_produto,
        p.preco,
        p.Preco_de_Venda,
        p.detalhes_produto,
        p.data_de_cadastro,
        c.id AS id_categoria,
        c.tipo_produto,
        f.nome AS nome_fornecedor,
        e.quantidade
    FROM produto p
    JOIN categoria c ON p.id_categoria = c.id
    JOIN produto_fornecedor pf ON p.id = pf.id_produto
    JOIN fornecedor f ON pf.id_fornecedor = f.id
    JOIN estoque e ON p.id = e.id_produto
    WHERE p.nome_produto LIKE ? OR f.nome LIKE ?
    ORDER BY p.data_de_cadastro DESC
    LIMIT 100;
""";

    public ObservableList<Produto> produtoEstoqueLIST() throws SQLException {
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlProdutoslist);

        while (rs.next()){
            Produto produto = new Produto();
            produto.setId(rs.getInt("id_produto"));
            produto.setNomeProduto(rs.getString("nome_produto"));
            produto.setPreco(rs.getDouble("preco"));
            produto.setPreco_De_venda(rs.getDouble("Preco_de_Venda"));
            produto.setDetalhes(rs.getString("detalhes_produto"));
            produto.setDataDeCadastro(rs.getDate("data_de_cadastro").toLocalDate());

            Categoria categoria = new Categoria();
            categoria.setId(rs.getInt("id_categoria"));
            categoria.setTipo_produto(rs.getString("tipo_produto"));
            produto.setCategoria(categoria);

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setNome(rs.getString("nome_fornecedor"));
            produto.setFornecedor(fornecedor);

            Estoque estoque = new Estoque();
            estoque.setQuantidade(rs.getInt("quantidade"));
            produto.setEstoque(estoque);

            lista.add(produto);

        }
        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }


    public ObservableList<Produto> buscaPorProduto (String nome) throws SQLException{
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sqlBusca);
        pstmt.setString(1,"%"+nome+"%");
        pstmt.setString(2,"%"+nome+"%");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Produto produto = new Produto();
            produto.setId(rs.getInt("id_produto"));
            produto.setNomeProduto(rs.getString("nome_produto"));
            produto.setPreco(rs.getDouble("preco"));
            produto.setPreco_De_venda(rs.getDouble("Preco_de_Venda"));
            produto.setDetalhes(rs.getString("detalhes_produto"));
            produto.setDataDeCadastro(rs.getDate("data_de_cadastro").toLocalDate());

            Categoria categoria = new Categoria();
            categoria.setId(rs.getInt("id_categoria"));
            categoria.setTipo_produto(rs.getString("tipo_produto"));
            produto.setCategoria(categoria);

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setNome(rs.getString("nome_fornecedor"));
            produto.setFornecedor(fornecedor);

            Estoque estoque = new Estoque();
            estoque.setQuantidade(rs.getInt("quantidade"));
            produto.setEstoque(estoque);

            lista.add(produto);
        }
        rs.close();
        pstmt.close();
        conn.close();
        return lista;
    }
}
