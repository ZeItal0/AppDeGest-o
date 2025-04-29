package DAOclass;

import Models.Sessao;
import software.consultoria.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchUsuarioDao {

    DatabaseConnector dbConnector = new DatabaseConnector();
    private String sqllogin = "SELECT id, nome FROM usuario WHERE loginUser = ? AND senha = ?";
    private String sqlloginUser = "SELECT COUNT(*) FROM usuario WHERE loginUser = ?";

    public boolean verificarlogin (String login, String senha) throws SQLException {

        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement stmtlogin = null;

        try {
            conn = dbConnector.connect();
            stmtlogin = conn.prepareStatement(sqllogin);
            stmtlogin.setString(1,login);
            stmtlogin.setString(2,senha);
            rs = stmtlogin.executeQuery();

            if (rs.next()){
                Sessao.nome = rs.getString("nome");
                Sessao.id = rs.getInt("id");
                return true;
            }
            else {
                return false;
            }

        }finally {
            if (rs != null) rs.close();
            if (stmtlogin != null) stmtlogin.close();
            if (conn != null) conn.close();

        }

    }

    public boolean VerificarUsuario(String usuario) throws SQLException {
        Connection conn = dbConnector.connect();

        PreparedStatement pstmtVerificar = conn.prepareStatement(sqlloginUser);
        pstmtVerificar.setString(1,usuario);
        ResultSet rs = pstmtVerificar.executeQuery();

        boolean ext = false;
        if (rs.next()){
            ext = rs.getInt(1) > 0;
        }
        rs.close();
        pstmtVerificar.close();
        conn.close();
        return ext;
    }




}
