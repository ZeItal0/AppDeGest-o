package software.consultoria;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Map;

public class VideosController {

    private Main main;
    private Transition transition = new Transition();

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
    private Label userName;

    @FXML
    private MediaView Video1;

    private MediaPlayer mediaPlayer;

    @FXML
    private Slider barraDeprogresso;
    @FXML
    private Slider Volume;

    public void initialize(){
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


    public void CadastroFornecedor(ActionEvent actionEvent) {
        trocarVideo("/videos/VALORANT   2025-04-27 01-58-31.mp4");
    }

    public void CadastroProduto(ActionEvent actionEvent) {
        trocarVideo("/videos/video.mp4");
    }

    public void CadastroFuncionario(ActionEvent actionEvent) {
        trocarVideo("/videos/video.mp4");
    }

    public void CadastroDespesas(ActionEvent actionEvent) {
        trocarVideo("/videos/video.mp4");
    }

    public void CadastroVenda(ActionEvent actionEvent) {
        trocarVideo("/videos/video.mp4");
    }

    public void ListagemErelatorio(ActionEvent actionEvent) {
        trocarVideo("/videos/video.mp4");
    }

    public void closed(ActionEvent actionEvent) {
        Platform.exit();
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

    public void voltar(ActionEvent actionEvent) { main.carregarCena("/login.fxml"); }

    public void Open(ActionEvent actionEvent) {
        transition.animarMenu(menuAberto,larguraMenu,vboxLateral,voltar);
        menuAberto = !menuAberto;

        Node[] imagens = {img1,img2,img3,img4,img5,img6};
        transition.animarComponentes(OpenPosition,distancia,distanciaImg,distanciaUser,Open,imagens,userName);
        OpenPosition = !OpenPosition;
    }

    private void trocarVideo(String caminho){
            URL videoUrl = getClass().getResource(caminho);
            Media media = new Media(videoUrl.toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            Video1.setMediaPlayer(mediaPlayer);
            ProgressoSlider();
            VolumeDovideo();
            mediaPlayer.play();
    }

    public void Pause(ActionEvent actionEvent) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
            mediaPlayer.pause();
        }
    }

    public void Play(ActionEvent actionEvent) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED){
            mediaPlayer.play();
        }
    }

    public void ProgressoSlider(){
        barraDeprogresso.setMin(0);
        barraDeprogresso.setMax(100);
        barraDeprogresso.setValue(0);

        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) ->{
            if (!barraDeprogresso.isValueChanging()){
             double total = mediaPlayer.getTotalDuration().toSeconds();
             double atual = newValue.toSeconds();
             barraDeprogresso.setValue((atual/total)*100);
            }
        });

        barraDeprogresso.valueChangingProperty().addListener((obs, wasChanging, isChanging) ->{
            if (!isChanging) {
                if (mediaPlayer != null){
                    double total = mediaPlayer.getTotalDuration().toSeconds();
                    mediaPlayer.seek(Duration.seconds(barraDeprogresso.getValue() / 100 * total));
                }
            }
        });
    }

    public void VolumeDovideo(){
        Volume.setMin(0);
        Volume.setMax(1);
        Volume.setValue(0.5);
        Volume.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (mediaPlayer != null){
                mediaPlayer.setVolume(newValue.doubleValue());
            }
        });

    }



}
