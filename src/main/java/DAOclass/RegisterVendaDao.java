package DAOclass;

import Models.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software.consultoria.DatabaseConnector;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class RegisterVendaDao {
    DatabaseConnector dbConnector = new DatabaseConnector();
    private String sqlvenda = "INSERT INTO venda (id_forma_de_pagamento, id_funcionario, id_status_venda,data_venda) VALUES (?,?,?,?)";
    private String sqlFormaDepagamento = "INSERT INTO forma_de_pagamento (tipo_de_pagamento) VALUES (?)";
    private String sqlStatus = "INSERT INTO status_venda (status_de_venda) VALUES (?)";
    private String sqlItens = "INSERT INTO itens_de_venda (id_venda, id_produto, quantidade, valor_unitario) VALUES (?,?,?,?)";
    private String sqlvendaList = """
        SELECT 
            v.id AS id_venda,
            v.data_venda,
            u.nome AS nome_funcionario,
            p.id AS id_produto,
            p.nome_produto,
            p.detalhes_produto,
            iv.quantidade,
            iv.valor_unitario,
            fp.tipo_de_pagamento,
            sv.status_de_venda
        FROM venda v
        JOIN funcionario f ON v.id_funcionario = f.id
        JOIN usuario u ON f.id_usuario = u.id
        JOIN forma_de_pagamento fp ON v.id_forma_de_pagamento = fp.id
        JOIN status_venda sv ON v.id_status_venda = sv.id
        JOIN itens_de_venda iv ON v.id = iv.id_venda
        JOIN produto p ON iv.id_produto = p.id
        ORDER BY v.data_venda DESC
    """;

    public void salvarVenda(List<Itens_de_Venda> itens, String pagamento, LocalDate data, String movimentacao, String status, Integer id_funcionario) throws SQLException {
        Connection conn = dbConnector.connect();
        PreparedStatement pstmtFormaPagamento = conn.prepareStatement(sqlFormaDepagamento, Statement.RETURN_GENERATED_KEYS);
        pstmtFormaPagamento.setString(1,pagamento);
        pstmtFormaPagamento.executeUpdate();

        ResultSet rsFormaPagamento = pstmtFormaPagamento.getGeneratedKeys();
        int idFormaPagamento = -1;
        if (rsFormaPagamento.next()){
            idFormaPagamento = rsFormaPagamento.getInt(1);
        }

        PreparedStatement pstmtstatus = conn.prepareStatement(sqlStatus, Statement.RETURN_GENERATED_KEYS);
        pstmtstatus.setString(1,status);
        pstmtstatus.executeUpdate();

        ResultSet rsStatus = pstmtstatus.getGeneratedKeys();
        int idStatus = -1;
        if(rsStatus.next()){
            idStatus = rsStatus.getInt(1);
        }

        PreparedStatement pstmtVenda = conn.prepareStatement(sqlvenda, Statement.RETURN_GENERATED_KEYS);
        pstmtVenda.setInt(1,idFormaPagamento);
        pstmtVenda.setInt(2,id_funcionario);
        pstmtVenda.setInt(3,idStatus);
        pstmtVenda.setDate(4, Date.valueOf(data));
        pstmtVenda.executeUpdate();

        ResultSet rsVenda = pstmtVenda.getGeneratedKeys();
        int idVenda = -1;
        if (rsVenda.next()){
            idVenda = rsVenda.getInt(1);
        }

        PreparedStatement pstmtItens = conn.prepareStatement(sqlItens);
        for (Itens_de_Venda item : itens){
            pstmtItens.setInt(1,idVenda);
            pstmtItens.setInt(2,item.getProduto().getId());
            pstmtItens.setInt(3,item.getQuantidade_itens());
            pstmtItens.setDouble(4,item.getValor_unitario());
            pstmtItens.addBatch();
        }
        pstmtItens.executeBatch();

        pstmtItens.close();
        pstmtVenda.close();
        pstmtstatus.close();
        pstmtFormaPagamento.close();
        conn.close();
    }

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


}
