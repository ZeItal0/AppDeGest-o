package software.consultoria;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.util.Duration;

public class AvisoController {


    @FXML
    private Pane pane;
    @FXML
    private Pane paneLoad;
    @FXML
    private ImageView movSet;

    @FXML
    private Pane paneSeta;

    private Stage stage;

    private Transition transition = new Transition();

    private PauseTransition pause = new PauseTransition(Duration.seconds(1));
    private PauseTransition pausestack = new PauseTransition(Duration.seconds(6));
    private PauseTransition pauseSeta = new PauseTransition(Duration.seconds(1));

    @FXML
    private Label MENSAGEM;
    private boolean confirmado = false;
    private String mensagemDoAviso;


    @FXML
    public void initialize() {
        if (pane != null){
            transition.fadeInPane(pane);
            pause.setOnFinished(event -> {
                Stage stage = (Stage) pane.getScene().getWindow();
                stage.close();
            });
            pause.play();
        }
        if (paneLoad != null){
            pausestack.setOnFinished(event ->{
                Stage stage = (Stage) paneLoad.getScene().getWindow();
                stage.close();
            });
            pausestack.play();
        }
        if (paneSeta != null){ //<- aqui faz um fade transition e tambem um pause para surgir
            transition.fadeInPane(paneSeta);
            transition.animarSeta(movSet);
            pauseSeta.setOnFinished(event ->{
                Stage stage = (Stage) paneSeta.getScene().getWindow();
                stage.close();
            });
            pauseSeta.play();
        }

    }

    public void avisoMensagem(String mensagem){
        this.mensagemDoAviso = mensagem;
        if (MENSAGEM != null) {
            MENSAGEM.setText(mensagem);
        }
    }

    @FXML
    public void OK(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public boolean isConfirmado(){
        return confirmado;
    }

    public void trueAction(ActionEvent actionEvent) {
        confirmado = true;
        if (stage != null){
            stage.close();
        }
    }
    public void falseAction(ActionEvent actionEvent) {
        confirmado = false;
        if (stage != null){
            stage.close();
        }
    }
}
