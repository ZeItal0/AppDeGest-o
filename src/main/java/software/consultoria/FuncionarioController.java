package software.consultoria;

import DAOclass.RegisterUsuarioDao;
import Models.Endereco;
import Models.Usuario;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class FuncionarioController {
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
            RegisterUsuarioDao registerUsuarioDao = new RegisterUsuarioDao();
            ObservableList<Usuario> observableList = FXCollections.observableArrayList(registerUsuarioDao.listarusuario());
            funcionariostableList.setItems(observableList);
            main = ScreenChange.getMainInstance();
    }

    public void cadastrar(ActionEvent actionEvent) {
        main.carregarCena("/CadastroFuncionario.fxml");
    }

    public void listaDefuncionarios(ActionEvent actionEvent) {
    }

    public void Editarfuncionario(ActionEvent actionEvent) {
        main.carregarCena("/EditarFuncionario.fxml");
    }

    public void closed(ActionEvent actionEvent) {
        Platform.exit();
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

    public void voltar(ActionEvent actionEvent) {
        main.carregarCena("/login.fxml");
    }

    public void minimized(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
