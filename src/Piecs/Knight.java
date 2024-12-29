package Piecs;

import Board.Square;
import main.MainClass;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static Board.Board.getSquaresBoard;

public class Knight extends Piece{
    public Knight(ChessColor color, Square currentSquare, BufferedImage pieceImg) {
        super(color, currentSquare, pieceImg);
    }



    @Override
    public List<Square> getRawLegalMoves() {
        LinkedList<Square> legalMoves = new LinkedList<>();
        Square[][] board = getSquaresBoard();

        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();

        for (int i = 2; i > -3; i--) {
            for (int k = 2; k > -3; k--) {
                if(Math.abs(i) == 2 ^ Math.abs(k) == 2) {
                    if (k != 0 && i != 0) {
                        try {
                            legalMoves.add(board[y + k][x + i]);
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                    }
                }
            }
        }

        return legalMoves;
    }
}
