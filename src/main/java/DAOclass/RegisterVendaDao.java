package DAOclass;

import Models.Itens_de_Venda;
import Models.StatusDeVenda;
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


}
