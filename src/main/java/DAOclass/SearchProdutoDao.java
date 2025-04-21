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

    private String sqlProdutoslist = "SELECT \n" +
            "    p.id,\n" +
            "    p.nome_produto,\n" +
            "    p.preco,\n" +
            "    p.Preco_de_Venda,\n" +
            "    p.detalhes_produto,\n" +
            "    p.data_de_cadastro,\n" +
            "    \n" +
            "    c.tipo_produto,\n" +
            "    \n" +
            "    f.nome,\n" +
            "    \n" +
            "    e.quantidade\n" +
            "FROM produto p\n" +
            "JOIN categoria c ON p.id_categoria = c.id\n" +
            "JOIN produto_fornecedor pf ON p.id = pf.id_produto\n" +
            "JOIN fornecedor f ON pf.id_fornecedor = f.id\n" +
            "JOIN estoque e ON p.id = e.id_produto;";

    private String sqlBusca = "SELECT \n" +
            "    p.id,\n" +
            "    p.nome_produto,\n" +
            "    p.preco,\n" +
            "    p.Preco_de_Venda,\n" +
            "    p.detalhes_produto,\n" +
            "    p.data_de_cadastro,\n" +
            "    c.tipo_produto,\n" +
            "    f.nome,\n" +
            "    e.quantidade\n" +
            "FROM produto p\n" +
            "JOIN categoria c ON p.id_categoria = c.id\n" +
            "JOIN produto_fornecedor pf ON p.id = pf.id_produto\n" +
            "JOIN fornecedor f ON pf.id_fornecedor = f.id\n" +
            "JOIN estoque e ON p.id = e.id_produto\n" +
            "WHERE p.nome_produto LIKE ?\n";

    public ObservableList<Produto> produtoEstoqueLIST() throws SQLException {
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlProdutoslist);

        while (rs.next()){
            Produto produto = new Produto();
            produto.setId(rs.getInt("id"));
            produto.setNomeProduto(rs.getString("nome_produto"));
            produto.setPreco(rs.getDouble("preco"));
            produto.setPreco_De_venda(rs.getDouble("Preco_de_Venda"));
            produto.setDetalhes(rs.getString("detalhes_produto"));
            produto.setDataDeCadastro(rs.getDate("data_de_cadastro").toLocalDate());

            Categoria categoria = new Categoria();
            categoria.setTipo_produto(rs.getString("tipo_produto"));
            produto.setCategoria(categoria);

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setNome(rs.getString("nome"));
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
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Produto produto = new Produto();
            produto.setId(rs.getInt("id"));
            produto.setNomeProduto(rs.getString("nome_produto"));
            produto.setPreco(rs.getDouble("preco"));
            produto.setPreco_De_venda(rs.getDouble("Preco_de_Venda"));
            produto.setDetalhes(rs.getString("detalhes_produto"));
            produto.setDataDeCadastro(rs.getDate("data_de_cadastro").toLocalDate());

            Categoria categoria = new Categoria();
            categoria.setTipo_produto(rs.getString("tipo_produto"));
            produto.setCategoria(categoria);

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setNome(rs.getString("nome"));
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
