package DAOclass;

import Models.Endereco;
import Models.Fornecedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software.consultoria.DatabaseConnector;

import java.sql.*;
import java.time.LocalDate;

public class RegisterProdutoDao {
    DatabaseConnector dbConnector = new DatabaseConnector();

    private String sqlist = "SElECT nome FROM fornecedor";
    private String sqlProduto = "INSERT INTO produto(id_categoria, preco, Preco_de_Venda, nome_produto, detalhes_produto) VALUES (?,?,?,?,?)";
    private String sqlCategoria ="INSERT INTO categoria(tipo_produto) VALUES (?)";
    private String sqlEstoque = "INSERT INTO estoque(id_produto, quantidade) VALUES (?,?)";
    private String sqlMovimentacao= "INSERT INTO movimentacao_de_estoque(id_produto, tipo_movimentacao, quantidade, data_movimentacao) VALUES (?,?,?,?)";


    public void salvarProduto(String Nome, Integer Quantidade, Double ValorInvestido, Double ValorDeVenda, String Categoria, String Detalhes, String entrada, LocalDate data) throws SQLException {
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


    }

    public ObservableList<Fornecedor> fornecedorLIST() throws SQLException {
        ObservableList<Fornecedor> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlist);
        while (rs.next()){
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setNome(rs.getString("nome"));
            lista.add(fornecedor);
        }
        return lista;
    }

}
