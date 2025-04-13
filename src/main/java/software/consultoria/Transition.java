package software.consultoria;

import javafx.animation.FadeTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

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

    public void fadeInButton(ImageView image){
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.9),image);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }



}
