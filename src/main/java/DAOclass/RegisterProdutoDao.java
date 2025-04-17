package DAOclass;

import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software.consultoria.DatabaseConnector;

import java.sql.*;
import java.time.LocalDate;

public class RegisterProdutoDao {
    DatabaseConnector dbConnector = new DatabaseConnector();

    private String sqlist = "SElECT id, nome FROM fornecedor";
    private String sqlProduto = "INSERT INTO produto(id_categoria, preco, Preco_de_Venda, nome_produto, detalhes_produto, data_de_cadastro) VALUES (?,?,?,?,?,?)";
    private String sqlCategoria ="INSERT INTO categoria(tipo_produto) VALUES (?)";
    private String sqlEstoque = "INSERT INTO estoque(id_produto, quantidade) VALUES (?,?)";
    private String sqlMovimentacao= "INSERT INTO movimentacao_de_estoque(id_produto, tipo_movimentacao, quantidade, data_movimentacao) VALUES (?,?,?,?)";
    private String sqlProdutoFornecedor = "INSERT INTO produto_fornecedor(id_fornecedor, id_produto, data_de_cadastro) VALUES (?,?,?)";
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


    public void salvarProduto(String Nome, Integer Quantidade, Double ValorInvestido, Double ValorDeVenda, String Categoria, String Detalhes, String entrada, LocalDate data, Integer idfornecedor) throws SQLException {
        Connection conn = dbConnector.connect();
        PreparedStatement pstmtCategoria = conn.prepareStatement(sqlCategoria,Statement.RETURN_GENERATED_KEYS);
        pstmtCategoria.setString(1,Categoria);
        pstmtCategoria.executeUpdate();

        ResultSet rsCategoria = pstmtCategoria.getGeneratedKeys();
        int idCategoria = -1;
        if (rsCategoria.next()){
            idCategoria = rsCategoria.getInt(1);
        }

        PreparedStatement pstmtProduto = conn.prepareStatement(sqlProduto,Statement.RETURN_GENERATED_KEYS);
        pstmtProduto.setInt(1,idCategoria);
        pstmtProduto.setDouble(2,ValorInvestido);
        pstmtProduto.setDouble(3,ValorDeVenda);
        pstmtProduto.setString(4,Nome);
        pstmtProduto.setString(5,Detalhes);
        pstmtProduto.setDate(6, Date.valueOf(data));
        pstmtProduto.executeUpdate();

        ResultSet rsProduto = pstmtProduto.getGeneratedKeys();
        int idProduto = -1;
        if (rsProduto.next()){
            idProduto = rsProduto.getInt(1);
        }

        PreparedStatement pstmtEstoque = conn.prepareStatement(sqlEstoque);
        pstmtEstoque.setInt(1,idProduto);
        pstmtEstoque.setInt(2,Quantidade);
        pstmtEstoque.executeUpdate();

        PreparedStatement pstmtMovimentacao = conn.prepareStatement(sqlMovimentacao);
        pstmtMovimentacao.setInt(1,idProduto);
        pstmtMovimentacao.setString(2,entrada);
        pstmtMovimentacao.setInt(3,Quantidade);
        pstmtMovimentacao.setDate(4, Date.valueOf(data));
        pstmtMovimentacao.executeUpdate();

        PreparedStatement pstmtProdutoFornecedor =  conn.prepareStatement(sqlProdutoFornecedor);
        pstmtProdutoFornecedor.setInt(1,idfornecedor);
        pstmtProdutoFornecedor.setInt(2,idProduto);
        pstmtProdutoFornecedor.setDate(3, Date.valueOf(data));
        pstmtProdutoFornecedor.executeUpdate();

        pstmtCategoria.close();
        pstmtProduto.close();
        pstmtEstoque.close();
        pstmtMovimentacao.close();
        pstmtProdutoFornecedor.close();
        conn.close();

    }

    public ObservableList<Fornecedor> fornecedorLIST() throws SQLException {
        ObservableList<Fornecedor> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlist);
        while (rs.next()){
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(rs.getInt("id"));
            fornecedor.setNome(rs.getString("nome"));
            lista.add(fornecedor);
        }
        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }

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
}
