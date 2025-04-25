package DAOclass;

import Models.RelatorioDoMes;
import software.consultoria.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioMensalDao {
    DatabaseConnector dbConnector = new DatabaseConnector();
    String sql = "SELECT strftime('%m', datetime(v.data_venda / 1000, 'unixepoch')) AS mes,\n" +
            "       SUM(iv.quantidade) AS total_vendas\n" +
            "FROM itens_de_venda iv\n" +
            "JOIN venda v ON iv.id_venda = v.id\n" +
            "WHERE strftime('%Y', datetime(v.data_venda / 1000, 'unixepoch')) = ?\n" +
            "GROUP BY mes\n" +
            "ORDER BY mes;";

    public List<RelatorioDoMes> buscarRelatorio(int ano){
        List<RelatorioDoMes> relatorios = new ArrayList<>();


        try (Connection conn = dbConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,String.valueOf(ano));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String mes = rs.getString("mes");
                double total = rs.getDouble("total_vendas");
                relatorios.add(new RelatorioDoMes(total, mes));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return  relatorios;
    }

}
