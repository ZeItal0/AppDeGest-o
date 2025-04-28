package software.consultoria;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import software.consultoria.AvisoController;

import java.awt.*;
import java.io.IOException;

public class Aviso{
    //Classe responsavel por carregar a cena de aviso dinamico e tambem aplica efeito de transparencia//
    public static void mostrarAviso(String mensagem,String fxpath){
        try {
            FXMLLoader loader = new FXMLLoader(Aviso.class.getResource(fxpath));
            Parent root = loader.load();
            AvisoController controller = loader.getController();
            controller.avisoMensagem(mensagem);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);

            if (fxpath.equals("/Seta.fxml")){
                new centralizarCenas().centerScene(stage,scene);
            }else {
                new centralizarCenas().centerAviso(stage,scene);
            }
            stage.showAndWait();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static boolean mostrarTrueAndFalse(String mensagem,String fxpath){
        try {
            FXMLLoader loader = new FXMLLoader(Aviso.class.getResource(fxpath));
            Parent root = loader.load();
            AvisoController controller = loader.getController();
            controller.avisoMensagem(mensagem);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            controller.setStage(stage);
            new centralizarCenas().centerAviso(stage,scene);
            stage.showAndWait();
            return controller.isConfirmado();

        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
