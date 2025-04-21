package software.consultoria;

import DAOclass.RegisterUsuarioDao;
import Models.Endereco;
import Models.Sessao;
import Models.Usuario;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class FuncionarioController {
    private Main main;
    @FXML
    private TableView<Usuario> funcionariostableList;
    @FXML
    private TableColumn<Usuario,String> nfuncionario;
    @FXML
    private TableColumn<Usuario,String> telFuncionario;
    @FXML
    private TableColumn<Usuario, String> cepFuncionario;
    @FXML
    private TableColumn<Usuario, String> endereco;
    @FXML
    private TableColumn<Usuario, LocalDate> dataNascimento;
    @FXML
    private TableColumn<Usuario, LocalDate> dataDecadastro;

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
    private Button voltar;
    @FXML
    private Pane pane;

    private Transition transition = new Transition();

    @FXML
    private Label userName;

    //esse initialize e usado para listagem de funcionarios alem de conter efeitos do menu principal//
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

            nfuncionario.setCellValueFactory(new PropertyValueFactory<>("nome"));
            telFuncionario.setCellValueFactory(new PropertyValueFactory<>("telefone"));

                cepFuncionario.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getEndereco().getCep())
                );

                endereco.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getEndereco().getRua())
                );

            dataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataDeNascimento"));
            dataDecadastro.setCellValueFactory(new PropertyValueFactory<>("DataDeRegistro"));
            RegisterUsuarioDao registerUsuarioDao = new RegisterUsuarioDao();
            ObservableList<Usuario> observableList = FXCollections.observableArrayList(registerUsuarioDao.listarusuario());
            funcionariostableList.setItems(observableList);
            main = ScreenChange.getMainInstance();

        userName.setText(Sessao.nome);
        if (pane != null){
            transition.fadeInPane(pane);
        }

    }

    //botoes para encerrar, minimizar e voltar//
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

    //Carregar cenas //
    public void cadastrar(ActionEvent actionEvent) {
        main.carregarCena("/CadastroFuncionario.fxml");
    }

    public void listaDefuncionarios(ActionEvent actionEvent) {
    }

    public void Editarfuncionario(ActionEvent actionEvent) {
        main.carregarCena("/EditarFuncionario.fxml");
    }

    //opcoes do menu principal//
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


    public void Open(ActionEvent actionEvent) {
        TranslateTransition transicao = new TranslateTransition(Duration.millis(300),vboxLateral);
        TranslateTransition transicaoSair = new TranslateTransition(Duration.millis(300),voltar);
        if (menuAberto) {
            transicao.setToX(-larguraMenu + 100);
            transicaoSair.setToX(-80);
        }
        else {
            transicao.setToX(0);
            transicaoSair.setToX(0);
        }
        transicao.play();
        transicaoSair.play();
        menuAberto = !menuAberto;

        TranslateTransition transicaobutton = new TranslateTransition(Duration.millis(300),Open);
        TranslateTransition transicaoimg1 = new TranslateTransition(Duration.millis(300),img1);
        TranslateTransition transicaoimg2 = new TranslateTransition(Duration.millis(300),img2);
        TranslateTransition transicaoimg3 = new TranslateTransition(Duration.millis(300),img3);
        TranslateTransition transicaoimg4 = new TranslateTransition(Duration.millis(300),img4);
        TranslateTransition transicaoimg5 = new TranslateTransition(Duration.millis(300),img5);
        TranslateTransition transicaoimg6 = new TranslateTransition(Duration.millis(300),img6);
        TranslateTransition transicaoUser = new TranslateTransition(Duration.millis(300),userName);
        if (!OpenPosition){
            transicaobutton.setToX(distancia);
            transicaoimg1.setToX(distanciaImg);
            transicaoimg2.setToX(distanciaImg);
            transicaoimg3.setToX(distanciaImg);
            transicaoimg4.setToX(distanciaImg);
            transicaoimg5.setToX(distanciaImg);
            transicaoimg6.setToX(distanciaImg);
            transicaoUser.setToX(distanciaUser);
        }
        else {
            transicaobutton.setToX(0);
            transicaoimg1.setToX(0);
            transicaoimg2.setToX(0);
            transicaoimg3.setToX(0);
            transicaoimg4.setToX(0);
            transicaoimg5.setToX(0);
            transicaoimg6.setToX(0);
            transicaoUser.setToX(0);
        }
        transicaobutton.play();
        transicaoimg1.play();
        transicaoimg2.play();
        transicaoimg3.play();
        transicaoimg4.play();
        transicaoimg5.play();
        transicaoimg6.play();
        transicaoUser.play();
        OpenPosition = !OpenPosition;
    }
}
