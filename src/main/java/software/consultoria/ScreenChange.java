package software.consultoria;

public class ScreenChange {

    //classe para capturar a classe main responsavel por carregar a cena e usar na controller para troca de cenas//
    private static Main mainInstance;

    public static void setMainInstance(Main main) {
        mainInstance = main;
    }

    public static Main getMainInstance() {
        return mainInstance;
    }
}