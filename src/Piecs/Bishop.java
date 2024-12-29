package Piecs;

import Board.Square;
import main.MainClass;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static Board.Board.getBoard;
import static Board.Board.getSquaresBoard;
import static main.Game.getGamePanel;

public class Bishop extends Piece {
    public Bishop(ChessColor color, Square currentSquare, BufferedImage pieceImg) {
        super(color, currentSquare, pieceImg);
    }


    @Override
    public List<Square> getRawLegalMoves() {
        Square[][] board = getSquaresBoard();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();

        return getDiagonalOccupations(board, x, y);
    }


}
