package Piecs;

import Board.Board;
import Board.Square;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import static Board.Board.getSquaresBoard;
import static Piecs.ChessColor.BLACK;
import static Piecs.ChessColor.WHITE;

public class Pawn extends Piece{
    private boolean wasMoved;

    public Pawn(ChessColor color, Square currentSquare, BufferedImage pieceImg) {
        super(color, currentSquare, pieceImg);
    }

    @Override
    public boolean move(Square fin, Board board) {
        int lastY = this.getPosition().getYNum();
        super.move(fin, board);
        if (lastY + 2 == this.getPosition().getYNum() || lastY - 2 == this.getPosition().getYNum()){
            Board.getBoard().setLastMovedPawn(this);
        }
        return true;
    }

    @Override
    public List<Square> getRawLegalMoves() {
        LinkedList<Square> legalMoves = new LinkedList<>();

        Square[][] board = getSquaresBoard();

        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        ChessColor c = this.getColor();

        if (c == ChessColor.WHITE) {
            if (!wasMoved) {
                if (!board[y + 1][x].isOccupied() && !board[y + 2][x].isOccupied()) {
                    legalMoves.add(board[y + 2][x]);
                }
            }
            //enPassant
            try {
                Piece lastPiece = Board.getBoard().getLastMovedPawn();
                if (lastPiece.getColor() == BLACK) {
                    if (board[y][x + 1].getOccupyingPiece() == lastPiece) legalMoves.add(board[y + 1][x + 1]);
                    else if (board[y][x - 1].getOccupyingPiece() == lastPiece) legalMoves.add(board[y + 1][x - 1]);
                }
            }catch (Exception e) {
            }

            if (y + 1 < 8) {
                if (!board[y + 1][x].isOccupied()) {
                    legalMoves.add(board[y + 1][x]);
                }
            }

            if (x + 1 < 8 && y + 1 < 8) {
                if (board[y + 1][x + 1].isOccupied()) {
                    legalMoves.add(board[y + 1][x + 1]);
                }
            }

            if (x - 1 >= 0 && y + 1 < 8) {
                if (board[y + 1][x - 1].isOccupied()) {
                    legalMoves.add(board[y + 1][x - 1]);
                }
            }





        }

        if (c == BLACK) {
            if (!wasMoved) {
                if (!board[y - 1][x].isOccupied() && !board[y - 2][x].isOccupied()) {
                    legalMoves.add(board[y - 2][x]);
                }
            }

            //enPassant
            try {
                Piece lastPiece = Board.getBoard().getLastMovedPawn();
                if (lastPiece.getColor() == WHITE) {
                    if (board[y][x + 1].getOccupyingPiece() == lastPiece) legalMoves.add(board[y - 1][x + 1]);
                    else if (board[y][x - 1].getOccupyingPiece() == lastPiece) legalMoves.add(board[y - 1][x - 1]);
                }
            }catch (Exception e) {
            }

            if (y - 1 >= 0) {
                if (!board[y - 1][x].isOccupied()) {
                    legalMoves.add(board[y - 1][x]);
                }
            }

            if (x + 1 < 8 && y - 1 >= 0) {
                if (board[y - 1][x + 1].isOccupied()) {
                    legalMoves.add(board[y - 1][x + 1]);
                }
            }

            if (x - 1 >= 0 && y - 1 >= 0) {
                if (board[y - 1][x - 1].isOccupied()) {
                    legalMoves.add(board[y - 1][x - 1]);
                }
            }



        }

        return legalMoves;
    }

   public List<Square> getEatingSquares(){

       LinkedList<Square> legalMoves = new LinkedList<>();

       Square[][] board = getSquaresBoard();

       int x = this.getPosition().getXNum();
       int y = this.getPosition().getYNum();
       ChessColor c = this.getColor();

       if (c == ChessColor.WHITE) {
           if (x + 1 < 8 && y + 1 < 8) {
               if (board[y + 1][x + 1].isOccupied()) {
                   legalMoves.add(board[y + 1][x + 1]);
               }
           }

           if (x - 1 >= 0 && y + 1 < 8) {
               if (board[y + 1][x - 1].isOccupied()) {
                   legalMoves.add(board[y + 1][x - 1]);
               }
           }

       }

       if (c == BLACK) {
           if (x + 1 < 8 && y - 1 >= 0) {
               if (board[y - 1][x + 1].isOccupied()) {
                   legalMoves.add(board[y - 1][x + 1]);
               }
           }

           if (x - 1 >= 0 && y - 1 >= 0) {
               if (board[y - 1][x - 1].isOccupied()) {
                   legalMoves.add(board[y - 1][x - 1]);
               }
           }
       }

       return legalMoves;

   }

    public Pawn setWasMoved(boolean wasMoved) {
        this.wasMoved = wasMoved;
        return this;
    }
}
