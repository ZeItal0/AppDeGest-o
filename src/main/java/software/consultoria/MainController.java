package software.consultoria;

import DAOclass.RegisterDespesaDao;
import DAOclass.RegisterFornecedorDao;
import DAOclass.RegisterUsuarioDao;
import Models.Sessao;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.util.Duration;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class MainController {
    RegisterUsuarioDao registerUsuarioDao = new RegisterUsuarioDao();

    RegisterFornecedorDao registerFornecedorDao = new RegisterFornecedorDao();

    RegisterDespesaDao registerDespesaDao = new RegisterDespesaDao();


    private Main main;
    @FXML
    private TextField NomeFuncionario;
    @FXML
    private TextField TelefoneFuncionario;
    @FXML
    private TextField CEP;
    @FXML
    private TextField rua;
    @FXML
    private TextField cidade;
    @FXML
    private TextField bairro;
    @FXML
    private DatePicker Data;
    @FXML
    private TextField Usuario;
    @FXML
    private TextField Senha;
    @FXML
    private MenuButton Cargo;

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
    private TextField NomeFornecedor;
    @FXML
    private TextField Telefonefornecedor;
    @FXML
    private TextField Email;
    @FXML
    private TextField cnpj;

    @FXML
    private TextField nomeDespesa;
    @FXML
    private TextField ValorDespesa;
    @FXML
    private MenuButton Situacao;
    @FXML
    private MenuButton FormaDepagamento;
    @FXML
    private TextArea ObservacoesDespesa;

    @FXML
    private Button buttonone;
    @FXML
    private Button buttontwo;


    @FXML
    private Pane pane;
    @FXML
    private Pane paneSuperior;
    @FXML
    private AnchorPane AnchorPane;

    private Transition transition = new Transition();

    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private Label userName;

    @FXML
    public void initialize() {
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

        if (userName != null){
            userName.setText(Sessao.nome);
        }
        if (pane != null){
            transition.fadeInPane(pane);
        }
        if (paneSuperior != null){
            transition.fadeInPane(paneSuperior);
        }
        if(buttonone!=null){
            transition.fadeInButtonsOptions(buttonone);
            transition.fadeInButtonsOptions(buttontwo);
        }
        if (AnchorPane!=null){
            transition.fadeInPane(AnchorPane);
        }

        main = ScreenChange.getMainInstance();
    }

    // codigos para encerrar minimizar e entrar no app//

    public void enter(ActionEvent actionEvent) throws SQLException {
        if(registerUsuarioDao.verificarlogin(user.getText(),password.getText()) == true){
            main.carregarCena("/menu.fxml");
        }
        if (user.getText().isEmpty() || password.getText().isEmpty()){
            Aviso.mostrarAviso("Digite o Usuario\nDigite a Senha!","/Alert.fxml");
        }
        if (registerUsuarioDao.verificarlogin(user.getText(),password.getText()) == false && !user.getText().isEmpty() && !password.getText().isEmpty()){
            Aviso.mostrarAviso("Usuario não Registrado!","/Alert.fxml");
        }
    }

    public void closed(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void minimized(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }


    //opcoes do menu principal para carregar cenas//
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

    public void voltar(ActionEvent actionEvent) { main.carregarCena("/login.fxml"); }


    //area de cadastro de funcionario e listagem e carregar cenas//
    public void funcionario(ActionEvent actionEvent) {
        main.carregarCena("/funcionariosOptions.fxml");
    }

    public void cadastrar(ActionEvent actionEvent) {
        main.carregarCena("/CadastroFuncionario.fxml");
    }

    public void listaDefuncionarios(ActionEvent actionEvent){
        main.carregarCena("/ListaFuncionario.fxml");
    }

    public void Editarfuncionario(ActionEvent actionEvent) {
        main.carregarCena("/EditarFuncionario.fxml");
    }

    public void CadastrarFuncionario(ActionEvent actionEvent) {

        String nome = NomeFuncionario.getText();
        String telefone = TelefoneFuncionario.getText();
        String cep = CEP.getText();
        String Rua = rua.getText();
        String Cidade = cidade.getText();
        String Bairro = bairro.getText();
        String User = Usuario.getText();
        String senha = Senha.getText();
        String ButtonMenu = Cargo.getText();
        LocalDate DataNas = Data.getValue();
        LocalDate DataReg = LocalDate.now();

        if (nome.isEmpty() || telefone.isEmpty() || cep.isEmpty() || Rua.isEmpty() || Cidade.isEmpty() || Bairro.isEmpty() || User.isEmpty() || senha.isEmpty() || ButtonMenu.isEmpty() || DataNas == null){
            Aviso.mostrarAviso("Preencha todos campos!","/Alert.fxml");
        }
        else {
            try {
                registerUsuarioDao.salvarusuario(nome,telefone,cep,Rua,Cidade,Bairro,User,senha,ButtonMenu,DataNas,DataReg);
                Aviso.mostrarAviso("","/confirmado.fxml");
            }
            catch (Exception e){
                e.printStackTrace();
                Aviso.mostrarAviso("Erro Registrar Usuario","/Alert.fxml");
            }
        }

    }
    public void selecionarVendedor(ActionEvent actionEvent) {
        Cargo.setText("Vendedor");
    }

    public void selecionarGerente(ActionEvent actionEvent) {
        Cargo.setText("Gerente");
    }

    public void selecionarChefe(ActionEvent actionEvent) {
        Cargo.setText("Chefe");
    }



    // area de cadastro de fornecedor, listagem e carregar cenas//
    public void cadastrarFornecedor(ActionEvent actionEvent) {
        main.carregarCena("/CadastroFornecedor.fxml");
    }

    public void listaFornecedores(ActionEvent actionEvent) {
        main.carregarCena("/ListaFornecedor.fxml");
    }

    public void confirmarFornecedor(ActionEvent actionEvent) {
        String nome = NomeFornecedor.getText();
        String telefone = Telefonefornecedor.getText();
        String email = Email.getText();
        String CNPJ = cnpj.getText();
        String cep = CEP.getText();
        String Rua = rua.getText();
        String Cidade = cidade.getText();
        String Bairro = bairro.getText();
        LocalDate DataReg = LocalDate.now();

        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty() || CNPJ.isEmpty() || cep.isEmpty() || Rua.isEmpty() || Cidade.isEmpty() || Bairro.isEmpty()){
            Aviso.mostrarAviso("Preencha todos campos!","/Alert.fxml");
        }
        else {
            try {
                registerFornecedorDao.salvarFornecedor(nome,telefone,email,CNPJ,cep,Rua,Cidade,Bairro,DataReg);
                Aviso.mostrarAviso("","/confirmado.fxml");
            }
            catch (Exception e){
                e.printStackTrace();
                Aviso.mostrarAviso("Erro Registrar fornecedor","/Alert.fxml");
            }
        }
    }

    //area de captura de informacoes ,listagem e carregar cenas//
    public void cadastrarDespesa(ActionEvent actionEvent) {
        main.carregarCena("/CadastroDespesa.fxml");
    }

    public void listaDedespesas(ActionEvent actionEvent) {
        main.carregarCena("/ListaDespesa.fxml");
    }


    public void SalvarDespesa(ActionEvent actionEvent) throws SQLException {
        String nome = nomeDespesa.getText();
        String valor = ValorDespesa.getText();
        String observacoes = ObservacoesDespesa.getText();
        String situacao = Situacao.getText();
        String pagamento = FormaDepagamento.getText();
        LocalDate data = Data.getValue();

        if (nome.isEmpty() || valor.isEmpty() || observacoes.isEmpty() || situacao.isEmpty() || pagamento.isEmpty() || data == null){
            Aviso.mostrarAviso("Preencha todos campos!","/Alert.fxml");
        }
        else {
            try {
                registerDespesaDao.salvardespesa(nome,Double.parseDouble(valor),observacoes,situacao,pagamento,data);
                Aviso.mostrarAviso("","/confirmado.fxml");
            }
            catch (Exception e) {
                e.printStackTrace();
                Aviso.mostrarAviso("Erro Registrar Despesa","/Alert.fxml");
            }
        }


    }
    public void selecionarPago(ActionEvent actionEvent) {
        Situacao.setText("Pago");
    }

    public void selecionarPedente(ActionEvent actionEvent) {
        Situacao.setText("Pendente");

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


    // parte para ser implementada //
    public void cadastrarProduto(ActionEvent actionEvent) {
        main.carregarCena("/CadastroProduto.fxml");
    }

    public void estoque(ActionEvent actionEvent) {
        main.carregarCena("/Estoque.fxml");
    }





    public void cadastrarvenda(ActionEvent actionEvent) {
        main.carregarCena("/RegistrarVendas.fxml");
    }

    public void historicoDevendas(ActionEvent actionEvent) {
        main.carregarCena("/ListaDeVendas.fxml");
    }


    //Metodo para fazer o efeito retratil do menu principal//
    public void Open(ActionEvent actionEvent) {
        TranslateTransition transicao = new TranslateTransition(Duration.millis(300),vboxLateral);
        TranslateTransition transicaoSair = new TranslateTransition(Duration.millis(300),voltar);
        if (menuAberto) {
            transicao.setToX(-larguraMenu + 100);
            transicaoSair.setToX(-80);
        }
        else {
            transicao.setToX(0);
            transicaoSair.setToX(0);
        }
        transicao.play();
        transicaoSair.play();
        menuAberto = !menuAberto;

        TranslateTransition transicaobutton = new TranslateTransition(Duration.millis(300),Open);
        TranslateTransition transicaoimg1 = new TranslateTransition(Duration.millis(300),img1);
        TranslateTransition transicaoimg2 = new TranslateTransition(Duration.millis(300),img2);
        TranslateTransition transicaoimg3 = new TranslateTransition(Duration.millis(300),img3);
        TranslateTransition transicaoimg4 = new TranslateTransition(Duration.millis(300),img4);
        TranslateTransition transicaoimg5 = new TranslateTransition(Duration.millis(300),img5);
        TranslateTransition transicaoimg6 = new TranslateTransition(Duration.millis(300),img6);
        TranslateTransition transicaoUser = new TranslateTransition(Duration.millis(300),userName);
        if (!OpenPosition){
            transicaobutton.setToX(distancia);
            transicaoimg1.setToX(distanciaImg);
            transicaoimg2.setToX(distanciaImg);
            transicaoimg3.setToX(distanciaImg);
            transicaoimg4.setToX(distanciaImg);
            transicaoimg5.setToX(distanciaImg);
            transicaoimg6.setToX(distanciaImg);
            transicaoUser.setToX(distanciaUser);
        }
        else {
            transicaobutton.setToX(0);
            transicaoimg1.setToX(0);
            transicaoimg2.setToX(0);
            transicaoimg3.setToX(0);
            transicaoimg4.setToX(0);
            transicaoimg5.setToX(0);
            transicaoimg6.setToX(0);
            transicaoUser.setToX(0);
        }
        transicaobutton.play();
        transicaoimg1.play();
        transicaoimg2.play();
        transicaoimg3.play();
        transicaoimg4.play();
        transicaoimg5.play();
        transicaoimg6.play();
        transicaoUser.play();
        OpenPosition = !OpenPosition;
    }
}