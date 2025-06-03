package software.consultoria;

import DAOclass.RegisterProdutoDao;
import DAOclass.RegisterVendaDao;
import DAOclass.SearchProdutoDao;
import Models.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
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

import java.io.ObjectInputFilter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class VendaRegisterController {
    Vendas vendas = new Vendas();
    RegisterVendaDao registerVendaDao = new RegisterVendaDao();

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

    private ObservableList<Itens_de_Venda> listaParaVenda = FXCollections.observableArrayList();
    @FXML
    private TableView <Itens_de_Venda> listaDeVenda;
    @FXML
    private TableColumn <Itens_de_Venda,String> NOMELIST;
    @FXML
    private TableColumn <Itens_de_Venda,String> DESCRICAOLIST;
    @FXML
    private TableColumn <Itens_de_Venda,Integer> QUANTIDADELIST;
    @FXML
    private TableColumn <Itens_de_Venda,Double> VALORLIST;


    private Main main;
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
    private Label DataAtual;
    @FXML
    private TextField DESCONTOPRODUTO;

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
    private Label TOTAL;
    @FXML
    private Button voltar;

    @FXML
    private MenuButton FormaDepagamento;

    @FXML
    private Label userName;
    @FXML
    private Label vendedor;
    @FXML
    private TextField Pesquisa;

    @FXML
    private Pane pane;
    private Transition transition = new Transition();

    // esse initialize e um pouco mais complexo ele captura uma lista de itens do estoque verifica se o item esta zerado ou nao e adiciona em outra lista para venda dos produtos//
    @FXML
    public void initialize() throws SQLException {

        userName.setText(Sessao.nome);
        vendedor.setText(Sessao.nome);
        DataAtual.setText(String.valueOf(LocalDate.now()));

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

        NOME.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        VALOR.setCellValueFactory(new PropertyValueFactory<>("preco_De_venda"));
        DESCRICAO.setCellValueFactory(new PropertyValueFactory<>("detalhes"));
        QUANTIDADE.setCellValueFactory( cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getEstoque().getQuantidade())
        );
        SearchProdutoDao searchProdutoDao = new SearchProdutoDao();
        ObservableList<Produto> observableList = FXCollections.observableArrayList(searchProdutoDao.produtoEstoqueLIST());
        produtosList.setItems(observableList);

        produtosList.setOnMouseClicked(event -> {
            if (event.getClickCount()==1){
                Produto selecionado = produtosList.getSelectionModel().getSelectedItem();
                if (selecionado != null){
                    int quantidade = selecionado.getEstoque().getQuantidade();
                    if (quantidade > 0){
                        selecionado.getEstoque().setQuantidade(quantidade - 1);
                        produtosList.refresh();
                        ListaDeVenda(selecionado);
                        Aviso.mostrarAviso("","/Seta.fxml");
                    }
                }
            }
        });
        main = ScreenChange.getMainInstance();

        if (pane != null){
            transition.fadeInPane(pane);
        }

        Pesquisa.textProperty().addListener((observable, oldValue, newValue) ->{

            try {
                ObservableList<Produto> filtro = searchProdutoDao.buscaPorProduto(newValue);
                produtosList.setItems(filtro);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    //lista ou carrinho de venda que salva o itens adicionados//
    private void ListaDeVenda(Produto selecionado) {

        Itens_de_Venda itemExistente = null;
        for(Itens_de_Venda item: listaParaVenda){
            if (item.getProduto().getId() == selecionado.getId()){
                itemExistente = item;
                break;
            }
        }

        Double percentual = 0.0;

        if (DESCONTOPRODUTO != null && !DESCONTOPRODUTO.getText().isEmpty()){
            try {
                percentual = Double.parseDouble(DESCONTOPRODUTO.getText());
            }
            catch (NumberFormatException e){
                percentual = 0.0;
            }
        }

        if (itemExistente != null){
            itemExistente.setQuantidade_itens(itemExistente.getQuantidade_itens() + 1);
            itemExistente.setValor_unitario(itemExistente.getQuantidade_itens() * selecionado.getPreco_De_venda());
        }
        else {
            Itens_de_Venda novoItem = new Itens_de_Venda();
            novoItem.setProduto(selecionado);
            novoItem.setQuantidade_itens(1);
            novoItem.setValor_unitario(selecionado.getPreco_De_venda());
            listaParaVenda.add(novoItem);
            itemExistente = novoItem;
        }

        Double valorDesconto = itemExistente.getQuantidade_itens() * selecionado.getPreco_De_venda() - (itemExistente.getQuantidade_itens() * selecionado.getPreco_De_venda() * percentual / 100);
        itemExistente.setValor_unitario(valorDesconto);

        vendas.setItens(listaParaVenda);
        TOTAL.setText(vendas.TotalVenda().toString());
        listaDeVenda.setItems(listaParaVenda);

        NOMELIST.setCellValueFactory( cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getProduto().getNomeProduto())
        );
        VALORLIST.setCellValueFactory( cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getValor_unitario())
        );
        DESCRICAOLIST.setCellValueFactory( cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getProduto().getDetalhes())
        );
        QUANTIDADELIST.setCellValueFactory( cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getQuantidade_itens())
        );
        listaDeVenda.refresh();
    }

    //botoes para encerrar sair e minimizar//
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


    public void cadastrarvenda(ActionEvent actionEvent) {
    }

    public void historicoDevendas(ActionEvent actionEvent) {
        main.carregarCena("/ListaDeVendas.fxml");
    }

    //Opcoes de forma de pagamento //
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

    //Ao cancelar a cena reenicia e tudo e limpo//
    public void Cancelar(ActionEvent actionEvent) {
        main.carregarCena("/RegistrarVendas.fxml");
    }

    //chama a classe dao para registrar a venda no banco//
    public void FINALIZAR(ActionEvent actionEvent) throws SQLException {

        if (listaDeVenda.getItems().isEmpty()){
            Aviso.mostrarAviso("Adicione um produto\n    da lista!","/Alert.fxml");
            return;
        }
        if (FormaDepagamento.getText().isEmpty() || FormaDepagamento.getText().equals("FORMA DE PAGAMENTO")){
            Aviso.mostrarAviso("Selecione a forma\n    de Pagamento!","/Alert.fxml");
            return;
        }
        if (!DESCONTOPRODUTO.getText().matches(".*\\d.*") && !DESCONTOPRODUTO.getText().isEmpty()){
            Aviso.mostrarAviso("Somente Numeros\n    em Desconto","/Alert.fxml");
            return;
        }

        try {
            registerVendaDao.salvarVenda(listaParaVenda,FormaDepagamento.getText(),LocalDate.now(), String.valueOf(entradasEsaidas.saida), String.valueOf(StatusDeVenda.Local),Sessao.id);
            Aviso.mostrarAviso("","/confirmado.fxml");
        }
        catch (Exception e){
            e.printStackTrace();
            Aviso.mostrarAviso("Erro ao realizar a venda","/Alert.fxml");
        }
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
