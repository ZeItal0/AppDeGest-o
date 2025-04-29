package software.consultoria;

import DAOclass.RegisterDespesaDao;
import DAOclass.RegisterFornecedorDao;
import DAOclass.RegisterUsuarioDao;
import DAOclass.SearchUsuarioDao;
import Models.Sessao;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    SearchUsuarioDao searchUsuarioDao = new SearchUsuarioDao();
    RegisterFornecedorDao registerFornecedorDao = new RegisterFornecedorDao();
    RegisterDespesaDao registerDespesaDao = new RegisterDespesaDao();
    private Transition transition = new Transition();

    private Main main;
    private boolean menuAberto = true;
    private boolean OpenPosition = false;
    private double larguraMenu = 415;
    private double distancia = 165;
    private double distanciaImg = 250;
    private double distanciaUser = 180;

    @FXML
    private VBox vboxLateral;
    @FXML
    private Button funcionario,venda,produtos,fornecedores,despesas,videos,voltar,Open;
    @FXML
    private ImageView img1,img2,img3,img4,img5,img6;

    @FXML
    private TextField NomeFuncionario,TelefoneFuncionario,CEP,rua,cidade,bairro,Usuario,Senha;
    @FXML
    private DatePicker Data;
    @FXML
    private MenuButton Cargo;

    @FXML
    private TextField NomeFornecedor,Telefonefornecedor,Email,cnpj;

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
    private Button buttonone,buttontwo,buttonthree;

    @FXML
    private Pane pane,paneSuperior;
    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private Label userName;

    @FXML
    public void initialize() {
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
            transition.fadeInButtonsOptions(buttonthree);
        }


        main = ScreenChange.getMainInstance();
    }

    // codigos para encerrar minimizar e entrar no app//

    public void enter(ActionEvent actionEvent) throws SQLException {
        if(searchUsuarioDao.verificarlogin(user.getText(),password.getText()) == true){
            main.carregarCena("/menu.fxml");
        }
        if (user.getText().isEmpty() || password.getText().isEmpty()){
            Aviso.mostrarAviso("Digite o Usuario\nDigite a Senha!","/Alert.fxml");
        }
        if (searchUsuarioDao.verificarlogin(user.getText(),password.getText()) == false && !user.getText().isEmpty() && !password.getText().isEmpty()){
            Aviso.mostrarAviso("Usuario não Registrado!","/Alert.fxml");
        }
        if (user.getText().length() > 8 || password.getText().length() > 16){
            Aviso.mostrarAviso("Usuario e Senha Devem \nconter 8 Caracteres","/Alert.fxml");
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

    public void CadastrarFuncionario(ActionEvent actionEvent) throws SQLException {

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
            return;
        }
        
        if (NomeFuncionario.getText().length() > 30){
            Aviso.mostrarAviso("Nome deve conter\n 30 Caracteres","/Alert.fxml");
            return;
        }else if ( TelefoneFuncionario.getText().length() > 14){
            Aviso.mostrarAviso("Telefone deve conter ate\n 14 Caracteres","/Alert.fxml");
            return;
        }else if (CEP.getText().length() > 8) {
            Aviso.mostrarAviso("Cep deve conter ate\n 8 Caracteres","/Alert.fxml");
            return;
        }else if (rua.getText().length() > 35){
            Aviso.mostrarAviso("rua deve conter ate\n 35 Caracteres","/Alert.fxml");
            return;
        }else if (cidade.getText().length() > 30) {
            Aviso.mostrarAviso("Cidade deve conter ate\n 30 Caracteres","/Alert.fxml");
            return;
        }else if (bairro.getText().length() > 25) {
            Aviso.mostrarAviso("Bairro deve conter ate\n 25 Caracteres","/Alert.fxml");
            return;
        }else if (Usuario.getText().length() > 8) {
            Aviso.mostrarAviso("Usuario deve conter ate\n 8 Caracteres","/Alert.fxml");
            return;
        } else if (Senha.getText().length() > 16){
            Aviso.mostrarAviso("Senha deve conter ate\n 16 Caracteres","/Alert.fxml");
            return;
        } else if (searchUsuarioDao.VerificarUsuario(Usuario.getText()) == true) {
            Aviso.mostrarAviso("Usuario já está\n em uso!","/Alert.fxml");
            return;
        } else {
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
            return;
        }
        if (NomeFornecedor.getText().length() > 30){
            Aviso.mostrarAviso("Nome deve conter\n 30 Caracteres","/Alert.fxml");
            return;
        }else if (Telefonefornecedor.getText().length() > 14) {
            Aviso.mostrarAviso("Telefone deve conter ate\n 14 Caracteres","/Alert.fxml");
            return;
        }else if (Email.getText().length() > 25){
            Aviso.mostrarAviso("Email deve conter ate\n 25 Caracteres","/Alert.fxml");
            return;
        }else if (cnpj.getText().length() > 14){
            Aviso.mostrarAviso("CNPJ deve conter ate\n 14 Caracteres","/Alert.fxml");
            return;
        }else if (CEP.getText().length() > 8){
            Aviso.mostrarAviso("CEP deve conter ate\n 8 Caracteres","/Alert.fxml");
            return;
        }else if (rua.getText().length() > 35){
            Aviso.mostrarAviso("rua deve conter ate\n 35 Caracteres","/Alert.fxml");
            return;
        }else if (cidade.getText().length() > 30) {
            Aviso.mostrarAviso("Cidade deve conter ate\n 30 Caracteres","/Alert.fxml");
            return;
        }else if (bairro.getText().length() > 25) {
            Aviso.mostrarAviso("Bairro deve conter ate\n 25 Caracteres","/Alert.fxml");
            return;
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
            return;
        }
        if (nomeDespesa.getText().length() > 30){
            Aviso.mostrarAviso("Numero Maximo em\nDespesa é 30!","/Alert.fxml");
            return;
        }else if (!valor.matches("\\d+")){
            Aviso.mostrarAviso("Somente numeros\nEm Valor!","/Alert.fxml");
            return;
        }else if (ObservacoesDespesa.getText().length() >50){
            Aviso.mostrarAviso("Numero Maximo em\nObservações é 50!","/Alert.fxml");
            return;
        }else if (situacao.isEmpty()){
            Aviso.mostrarAviso("Selecione uma opção\nEm situação","/Alert.fxml");
            return;
        }else if (pagamento.isEmpty()){
            Aviso.mostrarAviso("Selecione uma opção\nEm pagamento","/Alert.fxml");
            return;
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

    public void selecionarPedente(ActionEvent actionEvent) {Situacao.setText("Pendente"); }

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


    // Codigos para botoes da area de produto e venda//
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

    public void Relatorio(ActionEvent actionEvent) {
        main.carregarCena("/relatorio.fxml");
    }


    //Metodo para fazer o efeito retratil do menu principal//
    public void Open(ActionEvent actionEvent) {
        transition.animarMenu(menuAberto,larguraMenu,vboxLateral,voltar);
        menuAberto = !menuAberto;

        Node[] imagens = {img1,img2,img3,img4,img5,img6};
        transition.animarComponentes(OpenPosition,distancia,distanciaImg,distanciaUser,Open,imagens,userName);
        OpenPosition = !OpenPosition;
    }

}