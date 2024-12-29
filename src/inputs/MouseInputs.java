package inputs;

import Board.Board;
import Board.Square;
import Detectores.CheckDetector;
import Detectores.CheckMate;
import Piecs.*;
import main.Game;
import main.MainClass;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private static boolean isWhiteTurn = true;
    private static boolean isFirstClick = true;
    private final Board board;
    private Piece currPiece;
    private Square currSquare;
    private boolean EnableDots = false;
    private int halfMoves = 0;
    private int fullMoves = 1;
    CheckDetector cd;


    public MouseInputs(Board board) {
        if (board == null) {
            System.out.println("the board is null");
        }
        this.board = board;


    }

    @Override
    public void mouseMoved(MouseEvent e) {


    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    public void doFirstClick(MouseEvent e) {
        try {
            currSquare = board.getSquare(e.getX(), e.getY());
            if (MainClass.IsDebugMode())
                System.out.println("youre in square, x:" + currSquare.getXNum() + "y: currSquare.getYNum()");

            currPiece = currSquare.getOccupyingPiece();

            if (currPiece == null)
                return;
            if (!isWhiteTurn) {
                if (currPiece.getColor() == ChessColor.WHITE)
                    return;
            } else if (currPiece.getColor() == ChessColor.BLACK)
                return;

            isFirstClick = !isFirstClick;
        } catch (Exception _) {
        }

    }

    public void doSecondClick(MouseEvent e) {
        if (currPiece == null)
            return;
        if (currSquare == null)
            return;

        try {
            currSquare = board.getSquare(e.getX(), e.getY());
            if (currPiece.getLegalMoves().contains(currSquare)) {
                halfMoves = currPiece.move(currSquare, board)? 0: halfMoves + 1;
                if (!isIsWhiteTurn()) fullMoves++;
                isFirstClick = !isFirstClick;
                isWhiteTurn = !isWhiteTurn;
                System.out.println(Board.getBoard().toFEN());
                if (isStalemate(isWhiteTurn()))
                    CheckMate.callDraw("draw by stalemate");
                else if (!enoughMaterial()) {
                    CheckMate.callDraw("draw by influence material");
                } else {
                    cd = Game.getGamePanel().getCd();
                    cd.checkForMate();
                }
            } else if (currSquare.getOccupyingPiece() != null)
                if (currSquare.getOccupyingPiece().getColor() == currPiece.getColor()) {
                    doFirstClick(e);
                    EnableDots = true;
                }
        } catch (Exception _) {
        }

    }

    public boolean isStalemate(boolean isWhiteTurn) {
        if (isWhiteTurn) {
            for (Piece WPiece : board.getWPieces()) {
                if (!(WPiece instanceof King king && king.isInCheck())) return false;
                if (!WPiece.getLegalMoves().isEmpty())
                    return false;
            }
        } else {
            for (Piece BPiece : board.getBPieces()) {
                if (!(BPiece instanceof King king && king.isInCheck())) return false;
                if (!BPiece.getLegalMoves().isEmpty())
                    return false;
            }
        }
        return true;
    }

    private boolean enoughMaterial() {

        int WBidhops = 0;
        int BBidhops = 0;
        int WKnights = 0;
        int BKnights = 0;


        for (Piece Wpiece : board.getWPieces()) {
            if (Wpiece instanceof Queen || Wpiece instanceof Rook || Wpiece instanceof Pawn)
                return true;
            if (Wpiece instanceof Bishop)
                WBidhops++;
            if (Wpiece instanceof Knight)
                WKnights++;
        }
        for (Piece Bpiece : board.getBPieces()) {
            if (Bpiece instanceof Queen || Bpiece instanceof Rook || Bpiece instanceof Pawn)
                return true;
            if (Bpiece instanceof Bishop)
                BBidhops++;
            if (Bpiece instanceof Knight)
                BKnights++;

        }
        return WBidhops >= 2 || BBidhops >= 2 || (WBidhops == 1 && WKnights >= 1) || (BBidhops == 1 && BKnights >= 1);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (isFirstClick) {
            doFirstClick(e);
            EnableDots = true;
        } else {
            doSecondClick(e);
            EnableDots = false;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    public boolean EnableDots() {
        return EnableDots;
    }

    public Piece currPiece() {
        return currPiece;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }
    public static boolean isIsWhiteTurn() {
        return isWhiteTurn;
    }

    public int getHalfMoves() {
        return halfMoves;
    }

    public int getFullMoves() {
        return fullMoves;
    }
}
