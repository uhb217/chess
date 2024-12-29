package main;

public class MainClass {
    private static Game game;

    public static final boolean isDebugMode = false;

    public static void main(String[] args) {
        game = new Game();
    }

    public static Game getGame() {
        return game;
    }

    public static boolean IsDebugMode() {
        return isDebugMode;
    }




}
