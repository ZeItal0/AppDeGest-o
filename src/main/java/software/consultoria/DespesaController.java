package software.consultoria;

import DAOclass.RegisterDespesaDao;
import DAOclass.RegisterUsuarioDao;
import Models.Despesas;
import Models.Usuario;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class DespesaController {

    private Main main;

    @FXML
    private TableView<Despesas> DespesasList;
    @FXML
    private TableColumn<Despesas, String> DESPESAS;
    @FXML
    private TableColumn<Despesas, LocalDate> DATADADESPESA;
    @FXML
    private TableColumn<Despesas, Double> VALOR;
    @FXML
    private TableColumn<Despesas, String> OBSERVACOES;
    @FXML
    private TableColumn<Despesas, String> STATUS;
    @FXML
    private TableColumn<Despesas, String> PAGAMENTO;

    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private ImageView img4;
    @FXML
    private ImageView img5;
    @FXML
    private ImageView img6;
    @FXML
    private Button funcionario;
    @FXML
    private Button venda;
    @FXML
    private Button produtos;
    @FXML
    private Button fornecedores;
    @FXML
    private Button despesas;
    @FXML
    private Button videos;
    @FXML
    private Pane pane;
    private Transition transition = new Transition();

    @FXML
    public void initialize() throws SQLException {

        if(funcionario!=null){
            Map<Button, ImageView> hoverMap = Map.of(
                    funcionario, img1,
                    venda, img2,
                    produtos, img3,
                    fornecedores, img4,
                    despesas, img5,
                    videos, img6
            );
            hoverMap.forEach((botao, imagem) -> {
                if (botao != null && imagem != null) {
                    botao.setOnMouseEntered(e -> {
                        if (!imagem.getStyleClass().contains("hover-img")) {
                            imagem.getStyleClass().add("hover-img");
                        }
                        transition.fadeInButton(imagem);
                    });
                    botao.setOnMouseExited(e -> imagem.getStyleClass().remove("hover-img"));
                }
            });
        }

        DESPESAS.setCellValueFactory(new PropertyValueFactory<>("nomeDadispesa"));
        DATADADESPESA.setCellValueFactory(new PropertyValueFactory<>("data"));
        VALOR.setCellValueFactory(new PropertyValueFactory<>("valor"));
        OBSERVACOES.setCellValueFactory(new PropertyValueFactory<>("observacoes"));
        STATUS.setCellValueFactory(new PropertyValueFactory<>("Status"));

        PAGAMENTO.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFormaDepagamento().getTipoDepagamento())
        );


        RegisterDespesaDao registerDespesaDao = new RegisterDespesaDao();
        ObservableList<Despesas> observableList = FXCollections.observableArrayList(registerDespesaDao.despesasLIST());
        DespesasList.setItems(observableList);
        main = ScreenChange.getMainInstance();

        if (pane != null){
            transition.fadeInPane(pane);
        }

    }
    //botoes de minimizar, encerrar e voltar//
    public void closed(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void voltar(ActionEvent actionEvent) {
        main.carregarCena("/login.fxml");
    }
    public void minimized(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void funcionario(ActionEvent actionEvent) {
        main.carregarCena("/funcionariosOptions.fxml");
    }

    public void venda(ActionEvent actionEvent) {
        main.carregarCena("/VendaOptions.fxml");
    }

    public void produtos(ActionEvent actionEvent) {
        main.carregarCena("/produtosOptions.fxml");
    }

    public void fornecedores(ActionEvent actionEvent) {
        main.carregarCena("/fornecedoresOptions.fxml");
    }

    public void despesas(ActionEvent actionEvent) {
        main.carregarCena("/DespesasOptions.fxml");
    }

    public void videos(ActionEvent actionEvent) {
        main.carregarCena("/VideoAulas.fxml");
    }

    public void cadastrarDespesa(ActionEvent actionEvent) {
        main.carregarCena("/CadastroDespesa.fxml");
    }

    public void listaDedespesas(ActionEvent actionEvent) {

    }

    public void EditarDespesas(ActionEvent actionEvent) {
    }
}
