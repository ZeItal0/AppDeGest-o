package software.consultoria;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.sql.Connection;


public class Main extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        ScreenChange.setMainInstance(this);
        carregarCena("/login.fxml");
        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection conn = dbConnector.connect();
        primaryStage.show();
    }

    public void carregarCena(String fxpath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxpath));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Image cursorImage = new Image(getClass().getResourceAsStream("/cursor/goldcursor.png"));
            ImageCursor goldCursor = new ImageCursor(cursorImage);
            root.setCursor(goldCursor);
            if (fxpath != "/login.fxml"){
                centerScene(primaryStage,scene);
            }else{
                logincenter(primaryStage);
            }
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void centerScene (Stage stage, Scene scene){
        stage.setX(210);
        stage.setY(84.5);
    }
    public void logincenter (Stage stage){
        stage.setX(512.0);
        stage.setY(154.0);
    }
}