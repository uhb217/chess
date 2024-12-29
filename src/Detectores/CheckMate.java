package Detectores;

import Piecs.ChessColor;
import main.GamePanel;
import main.MainClass;

public class CheckMate {

    //call the checkmate window and ask if start a new game or reviw the game
    public static void callCheckMate(ChessColor color){
        System.out.println("the " + color.name() + " won the game by checkmate");

    }

    public static void callDraw(String reason){
        System.out.println(reason);
    }

    public static void EndClock(ChessColor color){

    }
}
