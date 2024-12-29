package Piecs;

import Board.Square;
import main.MainClass;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static Board.Board.getSquaresBoard;

public class Queen extends Piece{
    public Queen(ChessColor color, Square currentSquare, BufferedImage pieceImg) {
        super(color, currentSquare, pieceImg);
    }



    @Override
    public List<Square> getRawLegalMoves() {
        LinkedList<Square> legalMoves = new LinkedList<>();
        Square[][] board = getSquaresBoard();

        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();

        int[] occups = getLinearOccupations(board, x, y);

        for (int i = occups[0]; i <= occups[1]; i++) {
            if (i != y) legalMoves.add(board[i][x]);
        }

        for (int i = occups[2]; i <= occups[3]; i++) {
            if (i != x) legalMoves.add(board[y][i]);
        }

        List<Square> bMoves = getDiagonalOccupations(board, x, y);

        legalMoves.addAll(bMoves);

        return legalMoves;
    }
}
