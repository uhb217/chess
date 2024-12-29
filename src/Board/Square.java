package Board;

import Piecs.ChessColor;
import Piecs.Piece;
import utilz.LoadSave;

import static Board.Board.*;
import static utilz.LoadSave.getLoadSave;


public class Square {

    private Board board;
    private Piece occupyingPiece;
    private boolean dispPiece;
    private final int xNum;
    private final int yNum;
    private final int x;
    private final int y;


    public Square(int yNum, int xNum, int x, int y) {
        this.xNum = xNum;
        this.yNum = yNum;
        this.x = x;
        this.y = y;
        dispPiece = true;

    }

    public void put(Piece p) {
        this.occupyingPiece = p;
        p.setThePosition(this);
//        System.out.println(STR."Placed \{p.getClass().getSimpleName()} on square \{xNum},\{yNum}");
    }

    public void removePiece() {
//        System.out.println(STR."Removing \{occupyingPiece != null ? occupyingPiece.getClass().getSimpleName() : "empty piece"} from square \{xNum},\{yNum}");
        this.occupyingPiece = null;
    }




    public void capture(Piece capturingPiece, Board board) {
        Piece capturedPiece = this.getOccupyingPiece();
        if (capturedPiece != null) {
            if (capturedPiece.getColor() == ChessColor.WHITE) {
                board.getWPieces().remove(capturedPiece);
            } else {
                board.getBPieces().remove(capturedPiece);
            }

        }
        this.occupyingPiece = capturingPiece;
    }





    public boolean isInSquare(int xDelta, int yDelta) {
        if (!(xDelta > x && xDelta < x + getSideOfSquare()))
            return false;
        if (!(yDelta > y && yDelta < y + getSideOfSquare()))
            return false;
        return true;
    }


    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }

    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }

    public int getXNum() {
        return this.xNum;
    }

    public int getYNum() {
        return this.yNum;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
