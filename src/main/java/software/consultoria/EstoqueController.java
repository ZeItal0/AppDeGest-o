package software.consultoria;

import DAOclass.RegisterProdutoDao;
import DAOclass.SearchProdutoDao;
import DAOclass.UpdateProdutosDao;
import Models.Categoria;
import Models.Fornecedor;
import Models.Produto;
import Models.Sessao;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.SimpleStyleableIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class EstoqueController {
    private Main main;
    UpdateProdutosDao updateProdutosDao = new UpdateProdutosDao();
    ObservableList<String> categorias = FXCollections.observableArrayList("Aneis","Relogios","Perfumes","Colar","Pulseira");
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
    @FXML
    private Label userName;
    @FXML
    private TextField Pesquisa;

    //esse initialize e usado para listagem do estoque, tambem contem efeitos visuais//
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
            MenuTransition.AplicarHover(hoverMap, transition);
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

        SearchProdutoDao searchProdutoDao = new SearchProdutoDao();
        ObservableList<Produto> observableList = FXCollections.observableArrayList(searchProdutoDao.produtoEstoqueLIST());
        EstoqueList.setItems(observableList);

        main = ScreenChange.getMainInstance();

        tabelaEditavel();

        userName.setText(Sessao.nome);
        if (pane != null){
            transition.fadeInPane(pane);
        }

        Pesquisa.textProperty().addListener((observable, oldValue, newValue) ->{

            try {
                ObservableList<Produto> filtro = searchProdutoDao.buscaPorProduto(newValue);
                EstoqueList.setItems(filtro);
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

    public void Open(ActionEvent actionEvent) {
        transition.animarMenu(menuAberto,larguraMenu,vboxLateral,voltar);
        menuAberto = !menuAberto;

        Node[] imagens = {img1,img2,img3,img4,img5,img6};
        transition.animarComponentes(OpenPosition,distancia,distanciaImg,distanciaUser,Open,imagens,userName);
        OpenPosition = !OpenPosition;
    }

    public void Save(ActionEvent actionEvent) throws SQLException {
        Produto produtoSelecionado = EstoqueList.getSelectionModel().getSelectedItem();
        System.out.println(produtoSelecionado.getId());
        if (produtoSelecionado != null){
            updateProdutosDao.atualizarProdduto(produtoSelecionado.getId(),produtoSelecionado.getCategoria(),produtoSelecionado.getPreco(),produtoSelecionado.getPreco_De_venda(),produtoSelecionado.getEstoque().getQuantidade(),produtoSelecionado.getDetalhes(),produtoSelecionado.getNomeProduto());
            Aviso.mostrarAviso("Atualizado com\nsucesso!","/confirmarEditado.fxml");
        }
        else {
            Aviso.mostrarAviso("Selecione um item\nda lista!","/Alert.fxml");
        }
    }

    public void tabelaEditavel(){
        EstoqueList.setEditable(true);

        NomeProduto.setCellFactory(TextFieldTableCell.forTableColumn());
        NomeProduto.setOnEditCommit(event ->{
            Produto produto = event.getRowValue();
            produto.setNomeProduto(event.getNewValue());
        });

        Categoria.setCellFactory(ComboBoxTableCell.forTableColumn(categorias));
        Categoria.setOnEditCommit(event -> {
            Produto produto = event.getRowValue();
            produto.getCategoria().setTipo_produto(event.getNewValue());
        });

        ValorDeCompra.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        ValorDeCompra.setOnEditCommit( event ->{
            Produto produto = event.getRowValue();
            produto.setPreco(event.getNewValue());
        });

        ValorDeVenda.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        ValorDeVenda.setOnEditCommit(event ->{
            Produto produto = event.getRowValue();
            produto.setPreco_De_venda(event.getNewValue());
        });

        Detalhes.setCellFactory(TextFieldTableCell.forTableColumn());
        Detalhes.setOnEditCommit(event ->{
            Produto produto = event.getRowValue();
            produto.setDetalhes(event.getNewValue());
        });

        quantidade.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantidade.setOnEditCommit(event ->{
            Produto produto = event.getRowValue();
            produto.getEstoque().setQuantidade(event.getNewValue());
        });

    }
}
