package DAOclass;

import Models.RelatorioDeDespesas;
import Models.RelatorioDoMes;
import software.consultoria.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioMensalDao {
    DatabaseConnector dbConnector = new DatabaseConnector();
    String sqlRelatorio ="SELECT strftime('%m', datetime(v.data_venda / 1000, 'unixepoch')) AS mes,\n" +
            "       SUM(iv.valor_unitario) AS total_vendas,\n" +
            "       SUM(iv.quantidade) AS total_quantidade\n" +
            "FROM itens_de_venda iv\n" +
            "JOIN venda v ON iv.id_venda = v.id\n" +
            "WHERE strftime('%Y', datetime(v.data_venda / 1000, 'unixepoch')) = ?\n" +
            "GROUP BY mes\n" +
            "ORDER BY mes;";

    String sqlRelatorioDespesa = """
            SELECT strftime('%m', datetime(data_despesa / 1000, 'unixepoch')) AS mes,
                    COALESCE(SUM(valor_da_despesa), 0) AS total_despesas
                    FROM historico_de_despesas
                    WHERE strftime('%Y', datetime(data_despesa / 1000, 'unixepoch')) = ?
                    AND strftime('%m', datetime(data_despesa / 1000, 'unixepoch')) = ?
                    GROUP BY mes
                    ORDER BY mes;
    """;

    String sqlRelatorioInvestimento ="""
            SELECT strftime('%m', datetime(data_de_cadastro / 1000, 'unixepoch')) AS mes,
                     COALESCE(SUM(preco), 0) AS total_investido
                     FROM produto
                     WHERE strftime('%Y', datetime(data_de_cadastro / 1000, 'unixepoch')) = ?
                     AND strftime('%m', datetime(data_de_cadastro / 1000, 'unixepoch')) = ?
                     GROUP BY mes
                     ORDER BY mes;
    """;

    public List<RelatorioDoMes> buscarRelatorio(int ano){
        List<RelatorioDoMes> relatorios = new ArrayList<>();


        try (Connection conn = dbConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlRelatorio)){
            stmt.setString(1,String.valueOf(ano));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String mes = rs.getString("mes");
                Double Valorobtido = rs.getDouble("total_vendas");
                int quantidade = rs.getInt("total_quantidade");
                relatorios.add(new RelatorioDoMes(mes,Valorobtido,quantidade));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return  relatorios;
    }

    public Double buscarRelatorioDespesa(int ano,String mesSelecionado){
        Double total = null;
        try (Connection conn = dbConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlRelatorioDespesa)){
            stmt.setString(1,String.valueOf(ano));
            stmt.setString(2,String.valueOf(mesSelecionado));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                String mes = rs.getString("mes");
                total = rs.getDouble("total_despesas");
                new RelatorioDeDespesas(mes,0,total);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return  total;
    }

    public Double buscarRelatorioInvestimento(int ano, String mesSelecionado){
        Double total = null;
        try (Connection conn = dbConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sqlRelatorioInvestimento)){
            stmt.setString(1,String.valueOf(ano));
            stmt.setString(2,String.valueOf(mesSelecionado));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                String mes = rs.getString("mes");
                total = rs.getDouble("total_investido");
                new RelatorioDeDespesas(mes,total,0);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return  total;
    }
}
