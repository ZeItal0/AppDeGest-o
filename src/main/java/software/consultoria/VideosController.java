package software.consultoria;

import Models.Sessao;
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
    private MediaPlayer mediaPlayer;
    private boolean menuAberto = true;
    private boolean OpenPosition = false;
    private double larguraMenu = 415;
    private double distancia = 165;
    private double distanciaImg = 250;
    private double distanciaUser = 200;

    @FXML
    private VBox vboxLateral;
    @FXML
    private ImageView img1,img2,img3,img4,img5,img6,img8;
    @FXML
    private Button funcionario,venda,produtos,fornecedores,despesas,videos,voltar,Open;
    @FXML
    private Slider barraDeprogresso,Volume;
    @FXML
    private Label userName;
    @FXML
    private MediaView Video1;

    public void initialize(){
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
        if (userName != null){
            userName.setText(Sessao.nome);
        }
        main = ScreenChange.getMainInstance();
    }


    public void CadastroFornecedor(ActionEvent actionEvent) {
        trocarVideo("/videos/video.mp4");
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
