package DAOclass;

import Models.Despesas;
import Models.FormaDepagamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software.consultoria.DatabaseConnector;

import java.sql.*;
import java.time.LocalDate;

public class RegisterDespesaDao {

    DatabaseConnector dbConnector = new DatabaseConnector();
    private String sqlFormaDepagamento = "INSERT INTO forma_de_pagamento (tipo_de_pagamento) VALUES (?)";
    private String sqlDespesas = "INSERT INTO historico_de_despesas (id_forma_de_pagamento, nome_despesa, data_despesa, valor_da_despesa, observacoes, status_da_despesa) VALUES (?,?,?,?,?,?)";
    private String sqlDespesasList = "SELECT \n" +
            "    f.tipo_de_pagamento ,\n" +
            "    h.nome_despesa,\n" +
            "    h.data_despesa,\n" +
            "    h.valor_da_despesa,\n" +
            "    h.observacoes,\n" +
            "    h.status_da_despesa\n" +
            "FROM \n" +
            "    historico_de_despesas h\n" +
            "JOIN \n" +
            "    forma_de_pagamento f ON h.id_forma_de_pagamento = f.id;\n";

    public void salvardespesa (String nome, Double valor, String observacoes, String situacao, String pagamento, LocalDate data) throws SQLException {
        Connection conn = dbConnector.connect();
        PreparedStatement pstmtFormaPagamento = conn.prepareStatement(sqlFormaDepagamento, Statement.RETURN_GENERATED_KEYS);
        pstmtFormaPagamento.setString(1,pagamento);
        pstmtFormaPagamento.executeUpdate();

        ResultSet rsFormaPagamento = pstmtFormaPagamento.getGeneratedKeys();
        int idFormaPagamento = -1;
        if (rsFormaPagamento.next()){
            idFormaPagamento = rsFormaPagamento.getInt(1);
        }

        PreparedStatement pstmtDespesas = conn.prepareStatement(sqlDespesas);
        pstmtDespesas.setInt(1,idFormaPagamento);
        pstmtDespesas.setString(2,nome);
        pstmtDespesas.setDate(3, Date.valueOf(data));
        pstmtDespesas.setDouble(4,valor);
        pstmtDespesas.setString(5,observacoes);
        pstmtDespesas.setString(6,situacao);
        pstmtDespesas.executeUpdate();

        System.out.println(nome + " " +valor+" " + observacoes + " " + situacao+" "+pagamento+" "+data);

    }

    public ObservableList<Despesas> despesasLIST() throws SQLException {
        ObservableList<Despesas> lista = FXCollections.observableArrayList();
        Connection conn = dbConnector.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlDespesasList);

        while (rs.next()){
            Despesas despesas = new Despesas();
            despesas.setNomeDadispesa(rs.getString("nome_despesa"));
            despesas.setData(rs.getDate("data_despesa").toLocalDate());
            despesas.setValor(rs.getDouble("valor_da_despesa"));
            despesas.setObservacoes(rs.getString("observacoes"));
            despesas.setStatus(rs.getString("status_da_despesa"));
            FormaDepagamento formaDepagamento = new FormaDepagamento();
            formaDepagamento.setTipoDepagamento(rs.getString("tipo_de_pagamento"));
            despesas.setFormaDepagamento(formaDepagamento);
            lista.add(despesas);
        }

        return lista;
    }

}
