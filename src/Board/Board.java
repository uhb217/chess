package Board;

import Piecs.ChessColor;
import Piecs.King;
import Piecs.Pawn;
import Piecs.Piece;

import java.util.ArrayList;
import java.util.List;

import static main.GameWindow.*;

public class Board {

  private static  King wKing , bKing;
    private static final Board board;
    public static final Square[][] SquaresBoard = new Square[8][8];
    private static final int SideOfSquare = 87;
    private final ArrayList<Piece> WPieces = new ArrayList<>();
    private final ArrayList<Piece> BPieces = new ArrayList<>();
    private Pawn lastMovedPawn = null;


    private Board() {
        for (int yNum = 0; yNum < 8; yNum++)
            for (int xNum = 0; xNum < 8; xNum++)
                SquaresBoard[yNum][xNum] = new Square(yNum, xNum,  (WINDOW_WIDTH - 790) + SideOfSquare * xNum, (WINDOW_HEIGHT - 60) - SideOfSquare * (yNum+1));
    }




    public ArrayList<Piece> getWPieces() {
        return WPieces;
    }

    public ArrayList<Piece> getBPieces() {
        return BPieces;
    }

    public static Board getBoard() {
        return board;
    }

    public static Square[][] getSquaresBoard() {
        return SquaresBoard;
    }

    public Square getSquare(int x, int y){
        for (Square[] squares : SquaresBoard)
            for (Square square : squares)
                if (square.isInSquare(x,y))
                    return square;
       return null;
    }


    public List<Piece> getCheckingPieces(ChessColor kingColor) {
        List<Piece> checkingPieces = new ArrayList<>();
        King king = kingColor == ChessColor.WHITE ? wKing : bKing;
        List<Piece> opponentPieces = kingColor == ChessColor.WHITE ? WPieces : BPieces;

        for (Piece piece : opponentPieces) {
            if (piece.getRawLegalMoves().contains(king.getPosition())) {
                checkingPieces.add(piece);
            }
        }

        return checkingPieces;
    }

    static {
        board = new Board();
        for (Piece WPiece : board.WPieces)
            if (WPiece instanceof King king)
                wKing = king;
        for (Piece BPiece : board.BPieces)
            if (BPiece instanceof  King king)
                bKing = king;
    }


    public static King getwKing() {
        return wKing;
    }

    public static King getbKing() {
        return bKing;
    }


    public void updateKingCheckStatus() {

        for (Piece WPiece : board.WPieces)
            if (WPiece instanceof King king)
                wKing = king;
        for (Piece BPiece : board.BPieces)
            if (BPiece instanceof  King king)
                bKing = king;

        wKing.stopCheck();
        bKing.stopCheck();


        for (Piece piece : BPieces) {
            if (piece.getRawLegalMoves().contains(wKing.getPosition())) {
                wKing.callCheck();
                break;
            }
        }

        for (Piece piece : WPieces) {
            if (piece.getRawLegalMoves().contains(bKing.getPosition())) {
                bKing.callCheck();
                break;
            }
        }
    }

    public static King getkingForColor(ChessColor color) {

        for (Piece WPiece : board.WPieces)
            if (WPiece instanceof King king)
                wKing = king;
        for (Piece BPiece : board.BPieces)
            if (BPiece instanceof  King king)
                bKing = king;

        if (color.equals(ChessColor.WHITE))
            return wKing;
        else return bKing;
    }

    public static int getSideOfSquare(){
        return SideOfSquare;
    }

    public Pawn getLastMovedPawn() {
        return lastMovedPawn;
    }

    public void setLastMovedPawn(Pawn lastMovedPawn) {
        this.lastMovedPawn = lastMovedPawn;
    }
}
