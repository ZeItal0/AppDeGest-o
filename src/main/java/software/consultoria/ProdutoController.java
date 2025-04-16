package software.consultoria;

import DAOclass.RegisterProdutoDao;
import Models.Fornecedor;
import Models.Sessao;
import Models.entradasEsaidas;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class ProdutoController {

    RegisterProdutoDao registerProdutoDao = new RegisterProdutoDao();

    private entradasEsaidas status;

    private Main main;

    private int idfornecedor;

    @FXML
    private TableView<Fornecedor> FornecedorProdutoList;
    @FXML
    private TableColumn<Fornecedor,String> fornecedorList;

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
        fornecedorList.setCellValueFactory(new PropertyValueFactory<>("nome"));
        RegisterProdutoDao registerProdutoDao = new RegisterProdutoDao();
        ObservableList<Fornecedor> observableList = FXCollections.observableArrayList(registerProdutoDao.fornecedorLIST());
        FornecedorProdutoList.setItems(observableList);

        main = ScreenChange.getMainInstance();

        FornecedorProdutoList.setOnMouseClicked(event -> {
            if (event.getClickCount()==1){
                Fornecedor selecionado = FornecedorProdutoList.getSelectionModel().getSelectedItem();
                if (selecionado != null){
                    System.out.println("id: "+selecionado.getId());
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


// area para cadastro de produto, listagem e carregar cenas//
    public void cadastrarProduto(ActionEvent actionEvent) {
        main.carregarCena("/CadastroProduto.fxml");
    }

    public void estoque(ActionEvent actionEvent) {
        main.carregarCena("/Estoque.fxml");
    }

    public void Editarproduto(ActionEvent actionEvent) {
    }

    public void Cadastrar_Produto_fornecedor(ActionEvent actionEvent) throws SQLException {
        String Nome = nomeProduto.getText();
        String Quantidade = quantidade.getText();
        String ValorInvestido = valorInvestido.getText();
        String ValorDeVenda = valorDeVenda.getText();
        String Categoria = categoria.getText();
        String Detalhes = detalhes.getText();


        registerProdutoDao.salvarProduto(Nome,Integer.parseInt(Quantidade),Double.parseDouble(ValorInvestido),Double.parseDouble(ValorDeVenda),Categoria,Detalhes,String.valueOf(entradasEsaidas.entrada), LocalDate.now(),idfornecedor);
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

}
