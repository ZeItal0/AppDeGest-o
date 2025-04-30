package software.consultoria;

import DAOclass.*;
import Models.Fornecedor;
import Models.Produto;
import Models.Sessao;
import Models.Usuario;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private TableColumn<Fornecedor,String> CNPJ;
    @FXML
    private TableColumn<Fornecedor, String> email;
    @FXML
    private TableColumn<Fornecedor, String> cepFornecedor;
    @FXML
    private TableColumn<Fornecedor, String> endereco;
    @FXML
    private TableColumn<Fornecedor, LocalDate> dataDecadastro;

    @FXML
    private VBox vboxLateral;
    @FXML
    private Button Open;

    private boolean menuAberto = true;
    private boolean OpenPosition = false;

    private double larguraMenu = 415;
    private double distancia = 165;
    private double distanciaImg = 250;
    private double distanciaUser = 200;

    @FXML
    private ImageView img1,img2,img3,img4,img5,img6,img8;

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

    UpdateFornecedorDao updateFornecedorDao = new UpdateFornecedorDao();

    @FXML
    private Label userName;
    @FXML
    private TextField Pesquisa;

    //esse initialize e usado para listagem de fornecedores alem de conter efeitos visuais//
    @FXML
    public void initialize() throws SQLException {

        if(funcionario!=null){
            ImageView[] imagens = {img1,img2,img3,img4,img5,img6};
            menuAberto = false;
            Transition.posicaoMenu(vboxLateral,Open,voltar,imagens,userName,distancia,distanciaImg,distanciaUser,larguraMenu);
            OpenPosition = true;
            Map<Button, ImageView> hoverMap = Map.of(
                    funcionario, img1,
                    venda, img2,
                    produtos, img3,
                    fornecedores, img4,
                    despesas, img5,
                    videos, img6
            );
            MenuTransition.AplicarHoverDiamante(Open,img8,transition);
            MenuTransition.AplicarHover(hoverMap, transition);
        }

        nFornecedor.setCellValueFactory(new PropertyValueFactory<>("nome"));
        telFornecedor.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        CNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));

        cepFornecedor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEndereco().getCep())
        );

        endereco.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEndereco().getRua())
        );

        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        dataDecadastro.setCellValueFactory(new PropertyValueFactory<>("dataDeCadastro"));
        SearchFornecedorDao searchFornecedorDao = new SearchFornecedorDao();
        ObservableList<Fornecedor> observableList = FXCollections.observableArrayList(searchFornecedorDao.listarFornecedor());
        fornecedorTableList.setItems(observableList);

        userName.setText(Sessao.nome);
        main = ScreenChange.getMainInstance();
        if (pane != null){
            transition.fadeInPane(pane);
        }
        tabelaEditavel();
        Pesquisa.textProperty().addListener((observable, oldValue, newValue) ->{
            try {
                ObservableList<Fornecedor> filtro = searchFornecedorDao.BuscarFornecedor(newValue);
                fornecedorTableList.setItems(filtro);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
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
        main.carregarCena("/CadastroFuncionario.fxml");
    }

    public void venda(ActionEvent actionEvent) {
        main.carregarCena("/RegistrarVendas.fxml");
    }

    public void produtos(ActionEvent actionEvent) {
        main.carregarCena("/CadastroProduto.fxml");
    }

    public void fornecedores(ActionEvent actionEvent) {
        main.carregarCena("/CadastroFornecedor.fxml");
    }

    public void despesas(ActionEvent actionEvent) {
        main.carregarCena("/CadastroDespesa.fxml");
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

    public void Open(ActionEvent actionEvent) {
        transition.animarMenu(menuAberto,larguraMenu,vboxLateral,voltar);
        menuAberto = !menuAberto;

        Node[] imagens = {img1,img2,img3,img4,img5,img6};
        transition.animarComponentes(OpenPosition,distancia,distanciaImg,distanciaUser,Open,imagens,userName);
        OpenPosition = !OpenPosition;
    }

    public void Save(ActionEvent actionEvent) throws SQLException {
        Fornecedor fornecedorSelecionado = fornecedorTableList.getSelectionModel().getSelectedItem();
        if (fornecedorSelecionado != null){
            updateFornecedorDao.atualizarFornecedor(fornecedorSelecionado.getId(),fornecedorSelecionado.getNome(),fornecedorSelecionado.getTelefone(),fornecedorSelecionado.getCnpj(),fornecedorSelecionado.getEndereco().getCep(),fornecedorSelecionado.getEndereco().getRua(),fornecedorSelecionado.getEmail());
            Aviso.mostrarAviso("Atualizado com\nsucesso!","/confirmarEditado.fxml");
        }
        else {
            Aviso.mostrarAviso("Selecione um item\nda lista!","/Alert.fxml");
        }
    }

    public void tabelaEditavel(){
        fornecedorTableList.setEditable(true);

        nFornecedor.setCellFactory(TextFieldTableCell.forTableColumn());
        nFornecedor.setOnEditCommit( event ->{
            Fornecedor fornecedor = event.getRowValue();
            fornecedor.setNome(event.getNewValue());
        });
        telFornecedor.setCellFactory(TextFieldTableCell.forTableColumn());
        telFornecedor.setOnEditCommit(event ->{
            Fornecedor fornecedor = event.getRowValue();
            fornecedor.setTelefone(event.getNewValue());
        });
        CNPJ.setCellFactory(TextFieldTableCell.forTableColumn());
        CNPJ.setOnEditCommit(event -> {
            Fornecedor fornecedor = event.getRowValue();
            fornecedor.setCnpj(event.getNewValue());
        });
        cepFornecedor.setCellFactory(TextFieldTableCell.forTableColumn());
        cepFornecedor.setOnEditCommit(event ->{
            Fornecedor fornecedor = event.getRowValue();
            fornecedor.getEndereco().setCep(event.getNewValue());
        });
        endereco.setCellFactory(TextFieldTableCell.forTableColumn());
        endereco.setOnEditCommit(event ->{
            Fornecedor fornecedor = event.getRowValue();
            fornecedor.getEndereco().setRua(event.getNewValue());
        });
        email.setCellFactory(TextFieldTableCell.forTableColumn());
        email.setOnEditCommit(event ->{
            Fornecedor fornecedor = event.getRowValue();
            fornecedor.setEmail(event.getNewValue());
        });

    }
}
