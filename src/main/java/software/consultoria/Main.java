package software.consultoria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;
import java.sql.Connection;


public class Main extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }
    //metodo start carrega a primeira cena//
    @Override
    public void start(Stage primaryStage) {
        Aviso.mostrarAviso("","/loading.fxml");
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        ScreenChange.setMainInstance(this);
        carregarCena("/login.fxml");
        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection conn = dbConnector.connect();
        primaryStage.show();
    }

    //metodo usado para carregar cenas dos fxmls tambem faz a troca de cursor//
    public void carregarCena(String fxpath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxpath));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Image cursorImage = new Image(getClass().getResourceAsStream("/cursor/goldcursor.png"));
            ImageCursor goldCursor = new ImageCursor(cursorImage);
            root.setCursor(goldCursor);
            if (fxpath == "/menu.fxml"){
                Aviso.mostrarAviso("","/loading.fxml");
            }
            if (fxpath != "/login.fxml"){
                new centralizarCenas().centerScene(primaryStage,scene);
            }else{
                new centralizarCenas().logincenter(primaryStage);
            }
            primaryStage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}