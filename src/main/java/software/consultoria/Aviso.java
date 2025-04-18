package software.consultoria;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import software.consultoria.AvisoController;

import java.awt.*;
import java.io.IOException;

public class Aviso {
    //Classe responsavel por carregar a cena de aviso dinamico e tambem aplica efeito de transparencia//
    public static void mostrarAviso(String mensagem){
        try {
            FXMLLoader loader = new FXMLLoader(Aviso.class.getResource("/Alert.fxml"));
            Parent root = loader.load();
            AvisoController controller = loader.getController();
            controller.avisoMensagem(mensagem);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            centerAviso(stage, scene);
            stage.showAndWait();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void centerAviso (Stage stage, Scene scene){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        double centroX = screenSize.getWidth()/2;
        double centroY = screenSize.getHeight()/2;

        stage.setOnShown(event -> {
            double width = stage.getWidth();
            double height = stage.getHeight();
            stage.setX(centroX - width / 2);
            stage.setY(centroY - height / 2);
        });
    }
}
