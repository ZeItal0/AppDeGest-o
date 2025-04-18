package software.consultoria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;

public class AvisoController {

    @FXML
    private Label MENSAGEM;


    @FXML
    public void initialize() {
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
