package software.consultoria;

import javafx.scene.image.ImageView;
import java.awt.*;
import java.util.Map;
import javafx.scene.control.Button;

public class MenuTransition {

    private MenuTransition(){

    }

    public static void AplicarHover(Map<Button, ImageView> hoverMap, Transition transition){
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

}
