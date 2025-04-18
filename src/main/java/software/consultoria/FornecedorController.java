package software.consultoria;

import DAOclass.RegisterFornecedorDao;
import DAOclass.RegisterUsuarioDao;
import Models.Fornecedor;
import Models.Sessao;
import Models.Usuario;
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
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class FornecedorController {
    private Main main;
    @FXML
    private TableView<Fornecedor> fornecedorTableList;
    @FXML
    private TableColumn<Fornecedor,String> nFornecedor;
    @FXML
    private TableColumn<Fornecedor,String> telFornecedor;
    @FXML
    private TableColumn<Fornecedor, String> email;
    @FXML
    private TableColumn<Fornecedor, String> cepFornecedor;
    @FXML
    private TableColumn<Fornecedor, String> endereco;
    @FXML
    private TableColumn<Fornecedor, LocalDate> dataDecadastro;
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
    private Label userName;

    //esse initialize e usado para listagem de fornecedores alem de conter efeitos visuais//
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

        nFornecedor.setCellValueFactory(new PropertyValueFactory<>("nome"));
        telFornecedor.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        cepFornecedor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEndereco().getCep())
        );

        endereco.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEndereco().getRua())
        );

        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        dataDecadastro.setCellValueFactory(new PropertyValueFactory<>("dataDeCadastro"));
        RegisterFornecedorDao registerFornecedorDao = new RegisterFornecedorDao();
        ObservableList<Fornecedor> observableList = FXCollections.observableArrayList(registerFornecedorDao.listarFornecedor());
        fornecedorTableList.setItems(observableList);

        userName.setText(Sessao.nome);
        main = ScreenChange.getMainInstance();
        if (pane != null){
            transition.fadeInPane(pane);
        }
    }

    //botoes de encerrar, voltar e minimizar//
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

    //area de carregar cenas da opcao especifica do menu//
    public void cadastrarFornecedor(ActionEvent actionEvent) {
        main.carregarCena("/CadastroFornecedor.fxml");
    }

    public void listaFornecedores(ActionEvent actionEvent) {
    }

}
