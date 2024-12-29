package Board;

import Piecs.*;
import main.GamePanel;

import java.util.ArrayList;
import java.util.List;

import static Piecs.ChessColor.BLACK;
import static Piecs.ChessColor.WHITE;
import static main.GameWindow.*;

public class Board {
    private GamePanel gamePanel;
    private static King wKing, bKing;
    private static final Board board;
    public static final Square[][] SquaresBoard = new Square[8][8];
    private static final int SideOfSquare = 87;
    private final ArrayList<Piece> WPieces = new ArrayList<>();
    private final ArrayList<Piece> BPieces = new ArrayList<>();
    private Pawn lastMovedPawn = null;


    private Board() {
        for (int yNum = 0; yNum < 8; yNum++)
            for (int xNum = 0; xNum < 8; xNum++)
                SquaresBoard[yNum][xNum] = new Square(yNum, xNum, (WINDOW_WIDTH - 790) + SideOfSquare * xNum, (WINDOW_HEIGHT - 60) - SideOfSquare * (yNum + 1));
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

    public Square getSquare(int x, int y) {
        for (Square[] squares : SquaresBoard)
            for (Square square : squares)
                if (square.isInSquare(x, y))
                    return square;
        return null;
    }


    public List<Piece> getCheckingPieces(ChessColor kingColor) {
        List<Piece> checkingPieces = new ArrayList<>();
        King king = kingColor == WHITE ? wKing : bKing;
        List<Piece> opponentPieces = kingColor == WHITE ? WPieces : BPieces;

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
            if (BPiece instanceof King king)
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
            if (BPiece instanceof King king)
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
            if (BPiece instanceof King king)
                bKing = king;

        if (color.equals(WHITE))
            return wKing;
        else return bKing;
    }

    public String toFEN() {
        StringBuilder fen = new StringBuilder();
        int emptySquares = 0;
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                Square square = SquaresBoard[y][x];
                if (square.isOccupied()) {
                    if (emptySquares > 0) {
                        fen.append(emptySquares);
                        emptySquares = 0;
                    }
                    fen.append(square.getOccupyingPiece().toFEN());
                } else
                    emptySquares++;
            }
            if (emptySquares > 0) {
                fen.append(emptySquares);
                emptySquares = 0;
            }
            if (y != 0)
                fen.append("/");
        }
        fen.append(" ");
        fen.append(gamePanel.isWhiteTurn() ? "w" : "b");
        fen.append(" ");
        fen.append(castlingRights2FEN());
        fen.append(" ");
        fen.append(enPassantSquare2FEN());
        fen.append(" ");
        fen.append(gamePanel.getMouseInputs().getHalfMoves());
        fen.append(" ");
        fen.append(gamePanel.getMouseInputs().getFullMoves());
        return fen.toString();
    }

    public static int getSideOfSquare() {
        return SideOfSquare;
    }

    public Pawn getLastMovedPawn() {
        return lastMovedPawn;
    }

    public void setLastMovedPawn(Pawn lastMovedPawn) {
        this.lastMovedPawn = lastMovedPawn;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    private String castlingRights2FEN() {
        StringBuilder castleRights = new StringBuilder();
        if (!wKing.wasMoved()) { // if the king is still in its initial position and hasn't moved
            // if the rooks is still in its initial position and hasn't moved
            if (getSquaresBoard()[0][7].getOccupyingPiece() instanceof Rook rook && !rook.wasMoved()) castleRights.append("K");
            if (getSquaresBoard()[0][0].getOccupyingPiece() instanceof Rook rook && !rook.wasMoved()) castleRights.append("Q");
        }
        if (!bKing.wasMoved()) { // if the king is still in its initial position and hasn't moved
            // if the rooks is still in its initial position and hasn't moved
            if (getSquaresBoard()[7][7].getOccupyingPiece() instanceof Rook rook && !rook.wasMoved()) castleRights.append("k");
            if (getSquaresBoard()[7][0].getOccupyingPiece() instanceof Rook rook && !rook.wasMoved()) castleRights.append("q");
        }
        return castleRights.toString();
    }
    private String enPassantSquare2FEN() {
        if (lastMovedPawn != null ){
            int x = lastMovedPawn.getPosition().getXNum();
            if (lastMovedPawn.getColor() == WHITE && ((getSquaresBoard()[3][x+1].getOccupyingPiece() instanceof Pawn pawn && pawn.getColor() == BLACK)|| (getSquaresBoard()[3][x-1].getOccupyingPiece() instanceof Pawn pawn1 && pawn1.getColor() == BLACK)))
                return (char) ('a' + x) + "3";
            if (lastMovedPawn.getColor() == BLACK && ((getSquaresBoard()[4][x+1].getOccupyingPiece() instanceof Pawn pawn && pawn.getColor() == WHITE)|| (getSquaresBoard()[4][x-1].getOccupyingPiece() instanceof Pawn pawn1 && pawn1.getColor() == WHITE)))
                return (char) ('a' + x) + "6";
        }
        return "-";
    }
}
