package software.consultoria;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class centralizarCenas {

    public void centerScene (Stage stage, Scene scene){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        double centroX = screenSize.getWidth()/2;
        double centroY = screenSize.getHeight()/2;
        stage.setX(centroX-740);
        stage.setY(centroY-430);
    }
    public void logincenter (Stage stage){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        double centroX = screenSize.getWidth()/2;
        double centroY = screenSize.getHeight()/2;
        stage.setX(centroX-420);
        stage.setY(centroY-250);
    }

    public void centerAviso (Stage stage, Scene scene){
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
