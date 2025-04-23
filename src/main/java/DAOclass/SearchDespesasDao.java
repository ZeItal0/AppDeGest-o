package DAOclass;

import Models.Despesas;
import Models.FormaDepagamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software.consultoria.DatabaseConnector;

import java.sql.*;

public class SearchDespesasDao {
    DatabaseConnector dbConnector = new DatabaseConnector();
    private String sqlDespesasList = "SELECT \n" +
            "    h.id AS id_despesa,\n" +
            "    f.id AS id_pagamento,\n" +
            "    f.tipo_de_pagamento ,\n" +
            "    h.nome_despesa,\n" +
            "    h.data_despesa,\n" +
            "    h.valor_da_despesa,\n" +
            "    h.observacoes,\n" +
            "    h.status_da_despesa\n" +
            "FROM \n" +
            "    historico_de_despesas h\n" +
            "JOIN \n" +
            "    forma_de_pagamento f ON h.id_forma_de_pagamento = f.id;\n" +
            "ORDER BY h.data_despesa DESC\n" +
            "LIMIT 100;";

    private String sqlBusca = "SELECT \n" +
            "    h.id AS id_despesa,\n" +
            "    f.id AS id_pagamento,\n" +
            "    f.tipo_de_pagamento,\n" +
            "    h.nome_despesa,\n" +
            "    h.data_despesa,\n" +
            "    h.valor_da_despesa,\n" +
            "    h.observacoes,\n" +
            "    h.status_da_despesa\n" +
            "FROM \n" +
            "    historico_de_despesas h\n" +
            "JOIN \n" +
            "    forma_de_pagamento f ON h.id_forma_de_pagamento = f.id\n" +
            "WHERE \n" +
            "    h.nome_despesa LIKE ? OR f.tipo_de_pagamento LIKE ? OR h.status_da_despesa LIKE ? OR h.observacoes LIKE ? OR h.valor_da_despesa LIKE ?\n" +
            "ORDER BY h.data_despesa DESC;" +
            "LIMIT 100;";

    public ObservableList<Despesas> despesasLIST() throws SQLException {
        ObservableList<Despesas> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlDespesasList);

        while (rs.next()){
            Despesas despesas = new Despesas();
            despesas.setId(rs.getInt("id_despesa"));
            despesas.setNomeDadispesa(rs.getString("nome_despesa"));
            despesas.setData(rs.getDate("data_despesa").toLocalDate());
            despesas.setValor(rs.getDouble("valor_da_despesa"));
            despesas.setObservacoes(rs.getString("observacoes"));
            despesas.setStatus(rs.getString("status_da_despesa"));
            FormaDepagamento formaDepagamento = new FormaDepagamento();
            formaDepagamento.setId(rs.getInt("id_pagamento"));
            formaDepagamento.setTipoDepagamento(rs.getString("tipo_de_pagamento"));
            despesas.setFormaDepagamento(formaDepagamento);
            lista.add(despesas);
        }
        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }

    public ObservableList<Despesas> buscaDespesa(String despesa) throws SQLException {
        ObservableList<Despesas> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sqlBusca);
        pstmt.setString(1,"%"+despesa+"%");
        pstmt.setString(2,"%"+despesa+"%");
        pstmt.setString(3,"%"+despesa+"%");
        pstmt.setString(4,"%"+despesa+"%");
        pstmt.setString(5,"%"+despesa+"%");
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()){
            Despesas despesas = new Despesas();
            despesas.setId(rs.getInt("id_despesa"));
            despesas.setNomeDadispesa(rs.getString("nome_despesa"));
            despesas.setData(rs.getDate("data_despesa").toLocalDate());
            despesas.setValor(rs.getDouble("valor_da_despesa"));
            despesas.setObservacoes(rs.getString("observacoes"));
            despesas.setStatus(rs.getString("status_da_despesa"));
            FormaDepagamento formaDepagamento = new FormaDepagamento();
            formaDepagamento.setId(rs.getInt("id_pagamento"));
            formaDepagamento.setTipoDepagamento(rs.getString("tipo_de_pagamento"));
            despesas.setFormaDepagamento(formaDepagamento);
            lista.add(despesas);
        }
        rs.close();
        pstmt.close();
        conn.close();
        return lista;
    }


}
