package software.consultoria;

public class ScreenChange {
    private static Main mainInstance;

    public static void setMainInstance(Main main) {
        mainInstance = main;
    }

    public static Main getMainInstance() {
        return mainInstance;
    }
}