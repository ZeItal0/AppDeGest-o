package software.consultoria;

import DAOclass.RegisterVendaDao;
import Models.Itens_de_Venda;
import Models.Sessao;
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


    //Esse initialize faz a listagem e tambem aplica efeitos
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

        RegisterVendaDao registerVendaDao = new RegisterVendaDao();
        ObservableList<Itens_de_Venda> observableList = FXCollections.observableArrayList(registerVendaDao.vendasList());
        listaDeVenda.setItems(observableList);

        userName.setText(Sessao.nome);
        main = ScreenChange.getMainInstance();
        if (pane != null){
            transition.fadeInPane(pane);
        }
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
}
