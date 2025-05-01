package software.consultoria;

import DAOclass.RegisterProdutoDao;
import Models.Fornecedor;
import Models.Sessao;
import Models.entradasEsaidas;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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

public class ProdutoController {

    RegisterProdutoDao registerProdutoDao = new RegisterProdutoDao();

    private entradasEsaidas status;

    private Main main;

    private Integer idfornecedor;

    @FXML
    private TableView<Fornecedor> FornecedorProdutoList;
    @FXML
    private TableColumn<Fornecedor,String> fornecedorList;

    @FXML
    private ImageView img1,img2,img3,img4,img5,img6,img8;

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
    private MenuButton categoria;
    @FXML
    private TextField nomeProduto;
    @FXML
    private TextField quantidade;
    @FXML
    private TextField valorInvestido;
    @FXML
    private TextField valorDeVenda;
    @FXML
    private TextArea detalhes;
    @FXML
    private Pane pane;
    private Transition transition = new Transition();
    @FXML
    private Label userName;

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

        fornecedorList.setCellValueFactory(new PropertyValueFactory<>("nome"));
        RegisterProdutoDao registerProdutoDao = new RegisterProdutoDao();
        ObservableList<Fornecedor> observableList = FXCollections.observableArrayList(registerProdutoDao.fornecedorLIST());
        FornecedorProdutoList.setItems(observableList);

        main = ScreenChange.getMainInstance();

        FornecedorProdutoList.setOnMouseClicked(event -> {
            if (event.getClickCount()==1){
                Fornecedor selecionado = FornecedorProdutoList.getSelectionModel().getSelectedItem();
                if (selecionado != null){
                    idfornecedor = selecionado.getId();
                }
            }
        });
        userName.setText(Sessao.nome);
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

// area para cadastro de produto, listagem e carregar cenas//
    public void cadastrarProduto(ActionEvent actionEvent) {
        main.carregarCena("/CadastroProduto.fxml");
    }

    public void estoque(ActionEvent actionEvent) {
        main.carregarCena("/Estoque.fxml");
    }


    public void Cadastrar_Produto_fornecedor(ActionEvent actionEvent) {
        String Nome = nomeProduto.getText();
        String Quantidade = quantidade.getText();
        String ValorInvestido = valorInvestido.getText();
        String ValorDeVenda = valorDeVenda.getText();
        String Categoria = categoria.getText();
        String Detalhes = detalhes.getText();

        if (Validacoes.validarProduto(Nome,Quantidade,ValorInvestido,ValorDeVenda,Categoria,Detalhes,idfornecedor)){
            try {
                registerProdutoDao.salvarProduto(Nome,Integer.parseInt(Quantidade),Double.parseDouble(ValorInvestido),Double.parseDouble(ValorDeVenda),Categoria,Detalhes,String.valueOf(entradasEsaidas.entrada), LocalDate.now(),idfornecedor);
                Aviso.mostrarAviso("","/confirmado.fxml");
                nomeProduto.clear();
                quantidade.clear();
                valorDeVenda.clear();
                valorInvestido.clear();
                detalhes.clear();
            }
            catch (Exception e){
                e.printStackTrace();
                Aviso.mostrarAviso("Erro ao cadastrar produto","/Alert.fxml");
            }
        }
    }

    public void selecionaranel(ActionEvent actionEvent) {
        categoria.setText("Aneis");
    }

    public void selecionarcolar(ActionEvent actionEvent) {
        categoria.setText("Colares");
    }

    public void selecionarBrinco(ActionEvent actionEvent) {
        categoria.setText("Brincos");
    }

    public void selecionarRelogio(ActionEvent actionEvent) {
        categoria.setText("Relogios");
    }

    public void selecionarperfume(ActionEvent actionEvent) {
        categoria.setText("Perfumes");
    }

    public void Open(ActionEvent actionEvent) {
        transition.animarMenu(menuAberto,larguraMenu,vboxLateral,voltar);
        menuAberto = !menuAberto;

        Node[] imagens = {img1,img2,img3,img4,img5,img6};
        transition.animarComponentes(OpenPosition,distancia,distanciaImg,distanciaUser,Open,imagens,userName);
        OpenPosition = !OpenPosition;
    }
}
