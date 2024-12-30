package Piecs;

import Board.Board;
import Board.Square;
import main.MainClass;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class King extends Piece {

    private boolean wasMoved = false;

    private boolean isInCheck = false;

    public King(ChessColor color, Square currentSquare, BufferedImage pieceImg) {
        super(color, currentSquare, pieceImg);
    }

    @Override
    public List<Square> getLegalMoves() {
        List<Square> legalMoves = getRawLegalMoves();
        legalMoves.removeIf(square -> {
            if (square.isOccupied() && square.getOccupyingPiece().getColor() == this.getColor()) {
                return true;
            }


            if (isProtected(square, Board.getBoard())) {
                return true;
            }


            Square originalPosition = this.getPosition();
            Piece capturedPiece = square.getOccupyingPiece();


            this.setPosition(square);
            if (this.isInCheck) {
                this.setPosition(originalPosition);
                if (capturedPiece != null) {
                    square.put(capturedPiece);
                }
                return true;
            }
            this.setPosition(originalPosition);
            if (capturedPiece != null) {
                square.put(capturedPiece);
            }
            return false;
        });
        return legalMoves;
    }





    @Override
    public List<Square> getRawLegalMoves() {
        try{
            List<Square> legalMoves = new ArrayList<>();
            Square[][] squaresBoard = Board.getSquaresBoard();
            int x = this.getPosition().getXNum();
            int y = this.getPosition().getYNum();


            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;
                    try {
                        Square targetSquare = squaresBoard[y + dy][x + dx];
                        if (!targetSquare.isOccupied() || targetSquare.getOccupyingPiece().getColor() != this.getColor())
                            legalMoves.add(targetSquare);

                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
            }

            if (!this.wasMoved()) {
                if (canCastle(squaresBoard, x, y, true)) {
                    legalMoves.add(squaresBoard[y][x + 2]);
                }
                if (canCastle(squaresBoard, x, y, false)) {
                    legalMoves.add(squaresBoard[y][x - 2]);
                }
            }

            return legalMoves;
        } catch (Exception e) {
        }
        return List.of();
    }

    public boolean canCastle(Square[][] squaresBoard, int x, int y, boolean kingSide) {
        int rookX = kingSide ? 7 : 0;
        int direction = kingSide ? 1 : -1;

        Piece rook = squaresBoard[y][rookX].getOccupyingPiece();
        if (!(rook instanceof Rook) || ((Rook) rook).wasMoved()) {
            return false;
        }

        for (int i = x + direction; i != rookX; i += direction) {
            if (squaresBoard[y][i].isOccupied()) {
                return false;
            }
        }
        if (this.getColor() == ChessColor.WHITE){
            for (Piece piece : Board.getBoard().getBPieces()) {
                if (piece instanceof King)
                    continue;
                if (piece.getRawLegalMoves().contains(squaresBoard[y][x]) || piece.getRawLegalMoves().contains(squaresBoard[y][x + direction])) {
                    return false;
                }
            }
        }else {
            for (Piece piece : Board.getBoard().getWPieces()) {
                if (piece instanceof King)
                    continue;
                if (piece.getRawLegalMoves().contains(squaresBoard[y][x]) || piece.getRawLegalMoves().contains(squaresBoard[y][x + direction])) {
                    return false;
                }
            }
        }


        return true;
    }



    public boolean isProtected(Square square, Board board) {
        List<Piece> enemyPieces = this.getColor() == ChessColor.WHITE ? board.getBPieces() : board.getWPieces();

        for (Piece enemy : enemyPieces) {
            if (!(enemy instanceof Pawn pawn)) {
                if (enemy.getRawLegalMoves().contains(square)) {
                    for (Piece protector : enemyPieces) {
                        if (protector.getRawLegalMoves().contains(enemy.getPosition())) {
                            return true;
                        }
                    }
                    return true;
                }
            }else {
                if (pawn.getEatingSquares().contains(square)) {
                    for (Piece protector : enemyPieces) {
                        if (protector.getRawLegalMoves().contains(enemy.getPosition())) {
                            return true;
                        }
                    }
                    return true;
                }

            }
        }
        return false;
    }







    public boolean wasMoved() {
        return wasMoved;
    }

    public void setWasMoved(boolean wasMoved) {
        this.wasMoved = wasMoved;
    }
    public boolean isInCheck() {
        return isInCheck;
    }

    public void callCheck(){
        this.isInCheck = true;
        if (MainClass.IsDebugMode())
            System.out.println("the " + getColor() + " king is in check");
    }

    public void stopCheck(){
        this.isInCheck = false;
        if (MainClass.IsDebugMode())
            System.out.println("the " + getColor() + " king is not check");
    }
}