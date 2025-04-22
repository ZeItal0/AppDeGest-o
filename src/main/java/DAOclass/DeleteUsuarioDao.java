package DAOclass;

import software.consultoria.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteUsuarioDao {
    DatabaseConnector dbConnector = new DatabaseConnector();
    String sqlDeletar = "DELETE FROM endereco WHERE id = ?";

    public void deletarFuncionario(int idendereco){
        try (Connection conn = dbConnector.connect()){
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(sqlDeletar)){
                pstmt.setInt(1,idendereco);
                int Deleted = pstmt.executeUpdate();

                if (Deleted > 0){
                    System.out.println("funcionario deletado");
                }
                conn.commit();
            }
            catch (SQLException e){
                conn.rollback();
                e.printStackTrace();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

}
