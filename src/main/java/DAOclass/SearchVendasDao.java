package DAOclass;

import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software.consultoria.DatabaseConnector;

import java.sql.*;

public class SearchVendasDao {
    DatabaseConnector dbConnector = new DatabaseConnector();
    private String sqlvendaList = """
        SELECT 
            v.id AS id_venda,
            v.data_venda,
            COALESCE(u.nome, 'REMOVIDO') AS nome_funcionario,
            p.id AS id_produto,
            p.nome_produto,
            p.detalhes_produto,
            iv.quantidade,
            iv.valor_unitario,
            fp.tipo_de_pagamento,
            sv.status_de_venda
        FROM venda v
        LEFT JOIN funcionario f ON v.id_funcionario = f.id
        LEFT JOIN usuario u ON f.id_usuario = u.id
        JOIN forma_de_pagamento fp ON v.id_forma_de_pagamento = fp.id
        JOIN status_venda sv ON v.id_status_venda = sv.id
        JOIN itens_de_venda iv ON v.id = iv.id_venda
        JOIN produto p ON iv.id_produto = p.id
        ORDER BY v.data_venda DESC
        LIMIT 100;
    """;
    private String sqlBusca = """
        SELECT 
            v.id AS id_venda,
            v.data_venda,
            COALESCE(u.nome, 'REMOVIDO') AS nome_funcionario,
            p.id AS id_produto,
            p.nome_produto,
            p.detalhes_produto,
            iv.quantidade,
            iv.valor_unitario,
            fp.tipo_de_pagamento,
            sv.status_de_venda
        FROM venda v
        LEFT JOIN funcionario f ON v.id_funcionario = f.id
        LEFT JOIN usuario u ON f.id_usuario = u.id
        JOIN forma_de_pagamento fp ON v.id_forma_de_pagamento = fp.id
        JOIN status_venda sv ON v.id_status_venda = sv.id
        JOIN itens_de_venda iv ON v.id = iv.id_venda
        JOIN produto p ON iv.id_produto = p.id
        WHERE p.nome_produto LIKE ? OR u.nome LIKE ?
        ORDER BY v.data_venda DESC 
        LIMIT 100;
    """;

    public ObservableList<Itens_de_Venda> vendasList() throws SQLException {
        ObservableList<Itens_de_Venda> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlvendaList);

        while (rs.next()){

            Produto produto = new Produto();
            produto.setNomeProduto(rs.getString("nome_produto"));
            produto.setDetalhes(rs.getString("detalhes_produto"));

            FormaDepagamento formaDepagamento = new FormaDepagamento();
            formaDepagamento.setTipoDepagamento(rs.getString("tipo_de_pagamento"));

            StatusDeVenda status = StatusDeVenda.valueOf(rs.getString("status_de_venda"));

            Usuario usuario = new Usuario();
            usuario.setNome(rs.getString("nome_funcionario"));

            Vendas vendas = new Vendas();
            vendas.setData_De_venda(rs.getDate("data_venda").toLocalDate());
            vendas.setFormaDepagamento(formaDepagamento);
            vendas.setStatusDeVenda(status);
            vendas.setUsuario(usuario);

            Itens_de_Venda item = new Itens_de_Venda();
            item.setProduto(produto);
            item.setQuantidade_itens(rs.getInt("quantidade"));
            item.setValor_unitario(rs.getDouble("valor_unitario"));
            item.setVendas(vendas);

            lista.add(item);
        }
        rs.close();
        stmt.close();
        conn.close();

        return lista;
    }

    public ObservableList<Itens_de_Venda> buscaPorVenda(String venda) throws SQLException {
        ObservableList<Itens_de_Venda> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sqlBusca);
        pstmt.setString(1,"%"+venda+"%");
        pstmt.setString(2,"%"+venda+"%");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){

            Produto produto = new Produto();
            produto.setNomeProduto(rs.getString("nome_produto"));
            produto.setDetalhes(rs.getString("detalhes_produto"));

            FormaDepagamento formaDepagamento = new FormaDepagamento();
            formaDepagamento.setTipoDepagamento(rs.getString("tipo_de_pagamento"));

            StatusDeVenda status = StatusDeVenda.valueOf(rs.getString("status_de_venda"));

            Usuario usuario = new Usuario();
            usuario.setNome(rs.getString("nome_funcionario"));

            Vendas vendas = new Vendas();
            vendas.setData_De_venda(rs.getDate("data_venda").toLocalDate());
            vendas.setFormaDepagamento(formaDepagamento);
            vendas.setStatusDeVenda(status);
            vendas.setUsuario(usuario);

            Itens_de_Venda item = new Itens_de_Venda();
            item.setProduto(produto);
            item.setQuantidade_itens(rs.getInt("quantidade"));
            item.setValor_unitario(rs.getDouble("valor_unitario"));
            item.setVendas(vendas);

            lista.add(item);
        }
        rs.close();
        pstmt.close();
        conn.close();

        return lista;
    }
}
