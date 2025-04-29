package software.consultoria;

import DAOclass.RegisterVendaDao;
import DAOclass.SearchVendasDao;
import Models.Itens_de_Venda;
import Models.Produto;
import Models.Sessao;
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
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class HistoricoVendasController {
    private Main main;

    @FXML
    private TableView<Itens_de_Venda> listaDeVenda;
    @FXML
    private TableColumn<Itens_de_Venda,String> FUNCIONARIO;
    @FXML
    private TableColumn<Itens_de_Venda,String> NOMELIST;
    @FXML
    private TableColumn<Itens_de_Venda,String> PAGAMENTO;
    @FXML
    private TableColumn<Itens_de_Venda,String> STATUS;
    @FXML
    private TableColumn<Itens_de_Venda, String> DATA;
    @FXML
    private TableColumn<Itens_de_Venda,Integer> QUANTIDADELIST;
    @FXML
    private TableColumn<Itens_de_Venda,Double> VALORLIST;

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
    @FXML
    private Label userName;

    @FXML
    private TextField Pesquisa;

    //Esse initialize faz a listagem e tambem aplica efeitos
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

        NOMELIST.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getProduto().getNomeProduto())
        );
        FUNCIONARIO.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getVendas().getUsuario().getNome())
        );
        PAGAMENTO.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getVendas().getFormaDepagamento().getTipoDepagamento())
        );
        DATA.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getVendas().getData_De_venda().toString())
        );
        STATUS.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getVendas().getStatusDeVenda().toString())
        );
        QUANTIDADELIST.setCellValueFactory(new PropertyValueFactory<>("quantidade_itens"));
        VALORLIST.setCellValueFactory(new PropertyValueFactory<>("valor_unitario"));

        SearchVendasDao searchVendasDao = new SearchVendasDao();
        ObservableList<Itens_de_Venda> observableList = FXCollections.observableArrayList(searchVendasDao.vendasList());
        listaDeVenda.setItems(observableList);

        userName.setText(Sessao.nome);
        main = ScreenChange.getMainInstance();
        if (pane != null){
            transition.fadeInPane(pane);
        }

        Pesquisa.textProperty().addListener((observable, oldValue, newValue) ->{
            try {
                ObservableList<Itens_de_Venda> filtro = searchVendasDao.buscaPorVenda(newValue);
                listaDeVenda.setItems(filtro);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }


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



    public void cadastrarvenda(ActionEvent actionEvent) {
        main.carregarCena("/RegistrarVendas.fxml");
    }

    public void historicoDevendas(ActionEvent actionEvent) {
    }

    public void Open(ActionEvent actionEvent) {
        transition.animarMenu(menuAberto,larguraMenu,vboxLateral,voltar);
        menuAberto = !menuAberto;

        Node[] imagens = {img1,img2,img3,img4,img5,img6};
        transition.animarComponentes(OpenPosition,distancia,distanciaImg,distanciaUser,Open,imagens,userName);
        OpenPosition = !OpenPosition;
    }

    public void Relatorio(ActionEvent actionEvent) {
        main.carregarCena("/relatorio.fxml");
    }
}
