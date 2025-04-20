package software.consultoria;

import javafx.animation.PauseTransition;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
            if (fxpath != "/login.fxml"){
                centerScene(primaryStage,scene);
            }else{
                logincenter(primaryStage);
            }
            if (fxpath.equals("/menu.fxml")){
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(event -> primaryStage.setScene(scene));
                pause.play();
            }else {
                primaryStage.setScene(scene);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //metodo para centralizar a cena //
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
}