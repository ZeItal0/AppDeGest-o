package software.consultoria;

import javafx.animation.FadeTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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



}
