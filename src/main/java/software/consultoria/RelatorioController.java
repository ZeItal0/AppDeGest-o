package software.consultoria;

import DAOclass.RelatorioMensalDao;
import Models.RelatorioDoMes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RelatorioController {
    private Main main;
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

    private static final String[] MESES_PT = {"Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez"};

    @FXML
    private Label userName;
    @FXML
    private DatePicker Data;
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
    private Transition transition = new Transition();
    RelatorioMensalDao relatorioMensalDao = new RelatorioMensalDao();
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
    private BarChart<String,Number> BarMeses;

    @FXML
    private CategoryAxis categoryX;
    @FXML
    private NumberAxis numberY;


    public void initialize(){
        categoryX.setLabel("Meses");
        numberY.setLabel("Quantidade");
        BarMeses.getData().clear();

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

        main = ScreenChange.getMainInstance();
    }


    public void closed(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void minimized(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void cadastrarvenda(ActionEvent actionEvent) {
        main.carregarCena("/RegistrarVendas.fxml");
    }

    public void historicoDevendas(ActionEvent actionEvent) {
        main.carregarCena("/ListaDeVendas.fxml");
    }

    public void Relatorio(ActionEvent actionEvent) {
    }

    public void Open(ActionEvent actionEvent) {
        transition.animarMenu(menuAberto,larguraMenu,vboxLateral,voltar);
        menuAberto = !menuAberto;

        Node[] imagens = {img1,img2,img3,img4,img5,img6};
        transition.animarComponentes(OpenPosition,distancia,distanciaImg,distanciaUser,Open,imagens,userName);
        OpenPosition = !OpenPosition;
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

    public void voltar(ActionEvent actionEvent) { main.carregarCena("/login.fxml"); }

    public void funcionario(ActionEvent actionEvent) {
        main.carregarCena("/funcionariosOptions.fxml");
    }

    @FXML
    private void gerarRelatorio(ActionEvent actionEvent){
        LocalDate dataSelecionada = Data.getValue();
        System.out.println("Data selecionada: " + dataSelecionada);

        if (dataSelecionada == null){
            Aviso.mostrarAviso("Por favor, selecione\numa data", "/Alert.fxml");
            return;
        }
        int ano = dataSelecionada.getYear();
        List<RelatorioDoMes> relatorioDoMes = relatorioMensalDao.buscarRelatorio(ano);

        BarMeses.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
//        series.setName("Ano " + ano);

        for (RelatorioDoMes relatorio : relatorioDoMes){
            String Mes = converterNumero(relatorio.getMes());
            series.getData().add(new XYChart.Data<>(Mes, relatorio.getTotal()));
        }
        BarMeses.getData().add(series);

    }
    private String converterNumero(String numero){
       int index = Integer.parseInt(numero) - 1;
        return MESES_PT[index];
    }

}
