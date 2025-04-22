package software.consultoria;

import DAOclass.RegisterDespesaDao;
import DAOclass.SearchDespesasDao;
import Models.Despesas;
import Models.Produto;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private VBox vboxLateral;
    @FXML
    private Button Open;

    private boolean menuAberto = true;
    private boolean OpenPosition = false;

    private double larguraMenu = 415;
    private double distancia = 165;
    private double distanciaImg = 250;
    private double distanciaUser = 180;

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
    private Button voltar;

    @FXML
    private Pane pane;
    private Transition transition = new Transition();
    @FXML
    private Label userName;
    @FXML
    private TextField Pesquisa;

    @FXML
    public void initialize() throws SQLException {

        if(funcionario!=null){

            larguraMenu = vboxLateral.getPrefWidth();
            menuAberto = false;
            vboxLateral.setTranslateX(-larguraMenu + 100);
            Open.setTranslateX(distancia);
            voltar.setTranslateX(-80);
            img1.setTranslateX(distanciaImg);
            img2.setTranslateX(distanciaImg);
            img3.setTranslateX(distanciaImg);
            img4.setTranslateX(distanciaImg);
            img5.setTranslateX(distanciaImg);
            img6.setTranslateX(distanciaImg);
            userName.setTranslateX(distanciaUser);
            OpenPosition = true;

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

        SearchDespesasDao searchDespesasDao = new SearchDespesasDao();
        ObservableList<Despesas> observableList = FXCollections.observableArrayList(searchDespesasDao.despesasLIST());
        DespesasList.setItems(observableList);
        main = ScreenChange.getMainInstance();

        if (pane != null){
            transition.fadeInPane(pane);
        }

        Pesquisa.textProperty().addListener((observable, oldValue, newValue) ->{
            try {
                ObservableList<Despesas> filtro = searchDespesasDao.buscaDespesa(newValue);
                DespesasList.setItems(filtro);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
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

    public void Open(ActionEvent actionEvent) {
        transition.animarMenu(menuAberto,larguraMenu,vboxLateral,voltar);
        menuAberto = !menuAberto;

        Node[] imagens = {img1,img2,img3,img4,img5,img6};
        transition.animarComponentes(OpenPosition,distancia,distanciaImg,distanciaUser,Open,imagens,userName);
        OpenPosition = !OpenPosition;
    }
}
