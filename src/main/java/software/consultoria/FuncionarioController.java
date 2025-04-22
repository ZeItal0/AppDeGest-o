package software.consultoria;

import DAOclass.DeleteUsuarioDao;
import javafx.scene.control.cell.TextFieldTableCell;
import DAOclass.SearchFuncionarioDao;
import DAOclass.UpdateUsuarioDao;
import Models.Endereco;
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

public class FuncionarioController {
    UpdateUsuarioDao updateUsuarioDao = new UpdateUsuarioDao();
    private Main main;
    @FXML
    private TableView<Usuario> funcionariostableList;
    @FXML
    private TableColumn<Usuario,String> nfuncionario;
    @FXML
    private TableColumn<Usuario,String> telFuncionario;
    @FXML
    private TableColumn<Usuario, String> cepFuncionario;
    @FXML
    private TableColumn<Usuario, String> endereco;
    @FXML
    private TableColumn<Usuario, LocalDate> dataNascimento;
    @FXML
    private TableColumn<Usuario, LocalDate> dataDecadastro;

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
    private Button voltar;
    @FXML
    private TextField Pesquisa;

    @FXML
    private Pane pane;

    private Transition transition = new Transition();

    @FXML
    private Label userName;

    //esse initialize e usado para listagem de funcionarios alem de conter efeitos do menu principal//
    @FXML
    public void initialize() throws SQLException {

        if(funcionario!=null){
            larguraMenu = vboxLateral.getPrefWidth();
            menuAberto = false;
            vboxLateral.setTranslateX(-larguraMenu + 100);
            Open.setTranslateX(distancia);
            voltar.setTranslateX(-80);
            img1.setTranslateX(distanciaImg);
            img2.setTranslateX(distanciaImg);
            img3.setTranslateX(distanciaImg);
            img4.setTranslateX(distanciaImg);
            img5.setTranslateX(distanciaImg);
            img6.setTranslateX(distanciaImg);
            userName.setTranslateX(distanciaUser);
            OpenPosition = true;

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
            nfuncionario.setCellValueFactory(new PropertyValueFactory<>("nome"));
            telFuncionario.setCellValueFactory(new PropertyValueFactory<>("telefone"));

                cepFuncionario.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getEndereco().getCep())
                );

                endereco.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getEndereco().getRua())
                );

            dataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataDeNascimento"));
            dataDecadastro.setCellValueFactory(new PropertyValueFactory<>("DataDeRegistro"));
            SearchFuncionarioDao searchFuncionarioDao = new SearchFuncionarioDao();
            ObservableList<Usuario> observableList = FXCollections.observableArrayList(searchFuncionarioDao.listarusuario());
            funcionariostableList.setItems(observableList);
            main = ScreenChange.getMainInstance();
            tabelaEditavel();

        userName.setText(Sessao.nome);
        if (pane != null){
            transition.fadeInPane(pane);
        }

        Pesquisa.textProperty().addListener((observable, oldValue, newValue) ->{

            try {
                ObservableList<Usuario> filtro = searchFuncionarioDao.buscaPorNome(newValue);
                funcionariostableList.setItems(filtro);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    //botoes para encerrar, minimizar e voltar//
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

    //Carregar cenas //
    public void cadastrar(ActionEvent actionEvent) {
        main.carregarCena("/CadastroFuncionario.fxml");
    }

    public void listaDefuncionarios(ActionEvent actionEvent) {
    }

    public void Editarfuncionario(ActionEvent actionEvent) {
        main.carregarCena("/EditarFuncionario.fxml");
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


    public void Open(ActionEvent actionEvent) {
        transition.animarMenu(menuAberto,larguraMenu,vboxLateral,voltar);
        menuAberto = !menuAberto;

        Node[] imagens = {img1,img2,img3,img4,img5,img6};
        transition.animarComponentes(OpenPosition,distancia,distanciaImg,distanciaUser,Open,imagens,userName);
        OpenPosition = !OpenPosition;
    }

    public void Delete(ActionEvent actionEvent) throws SQLException {
        Usuario funcionarioSelecionado = funcionariostableList.getSelectionModel().getSelectedItem();
        System.out.println(funcionarioSelecionado.getEndereco().getId());

        if (funcionarioSelecionado != null){
            boolean confirmar = Aviso.mostrarTrueAndFalse("Tem certeza de deletar\n","/AlertTrueFalse.fxml");

            if (confirmar){
                DeleteUsuarioDao deleteUsuarioDao = new DeleteUsuarioDao();
                deleteUsuarioDao.deletarFuncionario(funcionarioSelecionado.getEndereco().getId());
                Aviso.mostrarAviso("Funcionario deletado\ncom sucesso!","/Alert.fxml");
                funcionariostableList.getItems().remove(funcionarioSelecionado);
            }
        }else {
            Aviso.mostrarAviso("Selecione um item\nda lista!","/Alert.fxml");
        }
    }

    public void Save(ActionEvent actionEvent) throws SQLException {
        Usuario funcionarioSelecionado = funcionariostableList.getSelectionModel().getSelectedItem();
        if (funcionarioSelecionado != null){
            updateUsuarioDao.atualizarFuncionario(funcionarioSelecionado.getId(),funcionarioSelecionado.getNome(),funcionarioSelecionado.getTelefone(),funcionarioSelecionado.getEndereco().getCep(),funcionarioSelecionado.getEndereco().getRua());
            Aviso.mostrarAviso("Atualizado com\nsucesso!","/Alert.fxml");
        }
        else {
            Aviso.mostrarAviso("Selecione um item\nda lista!","/Alert.fxml");
        }
    }
    public void tabelaEditavel(){
        funcionariostableList.setEditable(true);

        nfuncionario.setCellFactory(TextFieldTableCell.forTableColumn());
        nfuncionario.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.setNome(event.getNewValue());
        });

        telFuncionario.setCellFactory(TextFieldTableCell.forTableColumn());
        telFuncionario.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.setTelefone(event.getNewValue());
        });

        cepFuncionario.setCellFactory(TextFieldTableCell.forTableColumn());
        cepFuncionario.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.getEndereco().setCep(event.getNewValue());
        });
        endereco.setCellFactory(TextFieldTableCell.forTableColumn());
        endereco.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.getEndereco().setRua(event.getNewValue());
        });

    }
}
