package software.consultoria;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
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

    private Transition transition = new Transition();

    private PauseTransition pause = new PauseTransition(Duration.seconds(3));

    @FXML
    private Label MENSAGEM;


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

    }

    private String mensagemDoAviso;

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
}
