package DAOclass;

import software.consultoria.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDespesaDao {

    DatabaseConnector dbConnector = new DatabaseConnector();
    private String sqlUpdateFormaDePagamento = "UPDATE forma_de_pagamento SET tipo_de_pagamento = ? WHERE id = ?";
    private String sqlUpdateDespesa = "UPDATE historico_de_despesas SET nome_despesa = ?, valor_da_despesa = ?, observacoes = ?, status_da_despesa = ? WHERE id = ?";

    public void atualizarDespesa(int id, int idpagamento, String nomeDadispesa, Double valor, String observacoes, String status, String tipoDepagamento) throws SQLException {
        Connection conn = dbConnector.connect();

        PreparedStatement pstmtUpdatePagamento = conn.prepareStatement(sqlUpdateFormaDePagamento);
        pstmtUpdatePagamento.setString(1,tipoDepagamento);
        pstmtUpdatePagamento.setInt(2,idpagamento);
        pstmtUpdatePagamento.executeUpdate();

        PreparedStatement pstmtUpdadeDespesa = conn.prepareStatement(sqlUpdateDespesa);
        pstmtUpdadeDespesa.setString(1,nomeDadispesa);
        pstmtUpdadeDespesa.setDouble(2,valor);
        pstmtUpdadeDespesa.setString(3,observacoes);
        pstmtUpdadeDespesa.setString(4,status);
        pstmtUpdadeDespesa.setInt(5,id);
        pstmtUpdadeDespesa.executeUpdate();

        pstmtUpdatePagamento.close();
        pstmtUpdadeDespesa.close();
        conn.close();

    }
}
