package software.consultoria;

import DAOclass.RegisterProdutoDao;
import Models.Fornecedor;
import Models.Itens_de_Venda;
import Models.Produto;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
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

public class VendaRegisterController {

    @FXML
    private TableView <Produto> produtosList;
    @FXML
    private TableColumn <Produto,String> NOME;
    @FXML
    private TableColumn <Produto,String> DESCRICAO;
    @FXML
    private TableColumn <Produto,Integer> QUANTIDADE;
    @FXML
    private TableColumn <Produto,Double> VALOR;

    private ObservableList<Produto> listaParaVenda = FXCollections.observableArrayList();
    @FXML
    private TableView <Produto> listaDeVenda;
    @FXML
    private TableColumn <Produto,String> NOMELIST;
    @FXML
    private TableColumn <Produto,String> DESCRICAOLIST;
    @FXML
    private TableColumn <Produto,Integer> QUANTIDADELIST;
    @FXML
    private TableColumn <Produto,Double> VALORLIST;

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
    private Label DataAtual;

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
    private MenuButton FormaDepagamento;

    @FXML
    private Pane pane;
    private Transition transition = new Transition();

    @FXML
    public void initialize() throws SQLException {

        DataAtual.setText(String.valueOf(LocalDate.now()));

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
        NOME.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        VALOR.setCellValueFactory(new PropertyValueFactory<>("preco_De_venda"));
        DESCRICAO.setCellValueFactory(new PropertyValueFactory<>("detalhes"));
        QUANTIDADE.setCellValueFactory( cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getEstoque().getQuantidade())
        );
        RegisterProdutoDao registerProdutoDao = new RegisterProdutoDao();
        ObservableList<Produto> observableList = FXCollections.observableArrayList(registerProdutoDao.produtoEstoqueLIST());
        produtosList.setItems(observableList);

        produtosList.setOnMouseClicked(event -> {
            if (event.getClickCount()==1){
                Produto selecionado = produtosList.getSelectionModel().getSelectedItem();
                if (selecionado != null){
//                    System.out.println("nome: "+selecionado.getNomeProduto()+" preco: "+selecionado.getPreco_De_venda()+" detalhes: "+selecionado.getDetalhes()+" quantidade: "+selecionado.getEstoque().getQuantidade());
                    int quantidade = selecionado.getEstoque().getQuantidade();
                    if (quantidade > 0){
                        selecionado.getEstoque().setQuantidade(quantidade - 1);
                        produtosList.refresh();
                        ListaDeVenda(selecionado);
                    }
                }
            }
        });
        main = ScreenChange.getMainInstance();

        if (pane != null){
            transition.fadeInPane(pane);
        }
    }

    private void ListaDeVenda(Produto selecionado) {
        NOMELIST.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        VALORLIST.setCellValueFactory(new PropertyValueFactory<>("preco_De_venda"));
        DESCRICAOLIST.setCellValueFactory(new PropertyValueFactory<>("detalhes"));
        QUANTIDADELIST.setCellValueFactory( cellData ->
                new ReadOnlyObjectWrapper<>(1)
        );
        listaParaVenda.add(selecionado);
        listaDeVenda.setItems(listaParaVenda);
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
    }

    public void historicoDevendas(ActionEvent actionEvent) {
    }


    public void Pix(ActionEvent actionEvent) {
        FormaDepagamento.setText("Pix");
    }

    public void Dinheiro(ActionEvent actionEvent) {
        FormaDepagamento.setText("Dinheiro");
    }

    public void Credito(ActionEvent actionEvent) {
        FormaDepagamento.setText("Credito");
    }

    public void Debito(ActionEvent actionEvent) {
        FormaDepagamento.setText("Debito");
    }


    public void Cancelar(ActionEvent actionEvent) {
    }


    public void FINALIZAR(ActionEvent actionEvent) {
    }
}
