package software.consultoria;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;


public class Transition {
    // aqui e utilizado um fadetransition para o efeito das opcoes da tela//
    public void fadeInPane(Pane pane){
        if (pane == null){
            return;
        }
        pane.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.9),pane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
    public void fadeInButtonsOptions(Button botao){
        FadeTransition fadein = new FadeTransition(Duration.seconds(2),botao);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.play();
    }

    public void fadeInButton(ImageView image){
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.9),image);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    public void animarMenu(boolean menuAberto, double larguraMenu, Node vboxLateral, Node voltar){
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
    }
    public static void posicaoMenu(VBox vboxlateral, Button Open, Button voltar, ImageView[] imagens, Label userName, double distancia, double distanciaImg, double distanciaUser, double larguraMenu){
        larguraMenu = vboxlateral.getPrefWidth();
        vboxlateral.setTranslateX(-larguraMenu + 100);
        Open.setTranslateX(distancia);
        voltar.setTranslateX(-80);

        for (ImageView img : imagens){
            img.setTranslateX(distanciaImg);
        }
        userName.setTranslateX(distanciaUser);
    }


    public void animarComponentes(boolean openPosition, double destanciaBotao,double distanciaImg, double distanciaUser, Node botao, Node[] imagens,Node userName){
        TranslateTransition transitionBotao = new TranslateTransition(Duration.millis(300),botao);
        TranslateTransition transitionUser = new TranslateTransition(Duration.millis(300),userName);

        transitionBotao.setToX(openPosition ? 0 : destanciaBotao);
        transitionUser.setToX(openPosition ? 0 : distanciaUser);
        transitionBotao.play();
        transitionUser.play();

        for (Node img: imagens){
            TranslateTransition transitionImg = new TranslateTransition(Duration.millis(300),img);
            transitionImg.setToX(openPosition ? 0 : distanciaImg);
            transitionImg.play();
        }
    }
    public void animarSeta (ImageView imageView) {
        TranslateTransition transitionSeta = new TranslateTransition();
        transitionSeta.setNode(imageView);
        transitionSeta.setDuration(Duration.seconds(1));
        transitionSeta.setFromY(0);
        transitionSeta.setToY(260);
        transitionSeta.setInterpolator(Interpolator.EASE_OUT);
        transitionSeta.play();
    }

}
