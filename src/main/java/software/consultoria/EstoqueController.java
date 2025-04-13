package software.consultoria;

import DAOclass.RegisterProdutoDao;
import Models.Categoria;
import Models.Fornecedor;
import Models.Produto;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.SimpleStyleableIntegerProperty;
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

public class EstoqueController {
    private Main main;
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
    private TableView <Produto> EstoqueList;
    @FXML
    private TableColumn <Produto, String> NomeProduto;
    @FXML
    private TableColumn <Produto, Double> ValorDeCompra;
    @FXML
    private TableColumn <Produto, Double> ValorDeVenda;
    @FXML
    private TableColumn <Produto, String> Categoria;
    @FXML
    private TableColumn <Produto, String> NomeDoFornecedor;
    @FXML
    private TableColumn <Produto, LocalDate> dataDecadastro;
    @FXML
    private TableColumn <Produto, String> Detalhes;
    @FXML
    private TableColumn <Produto, Integer> quantidade;
    @FXML
    private Pane pane;
    private Transition transition = new Transition();

    //esse initialize e usado para listagem do estoque, tambem contem efeitos visuais//
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
        NomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        ValorDeCompra.setCellValueFactory(new PropertyValueFactory<>("preco"));
        ValorDeVenda.setCellValueFactory(new PropertyValueFactory<>("preco_De_venda"));
        Categoria.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCategoria().getTipo_produto())
        );
        NomeDoFornecedor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFornecedor().getNome())
        );
        dataDecadastro.setCellValueFactory(new PropertyValueFactory<>("dataDeCadastro"));
        Detalhes.setCellValueFactory(new PropertyValueFactory<>("detalhes"));
        quantidade.setCellValueFactory( cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getEstoque().getQuantidade())
        );

        RegisterProdutoDao registerProdutoDao = new RegisterProdutoDao();
        ObservableList<Produto> observableList = FXCollections.observableArrayList(registerProdutoDao.produtoEstoqueLIST());
        EstoqueList.setItems(observableList);

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

    //area para carregar cenas da opcao selecionada do menu principal//
    public void cadastrarProduto(ActionEvent actionEvent) {
        main.carregarCena("/CadastroProduto.fxml");
    }

    public void estoque(ActionEvent actionEvent) {
    }

    public void Editarproduto(ActionEvent actionEvent) {
    }
}
