package Piecs;

import Board.Board;
import Board.Square;
import Detectores.CheckDetector;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static Board.Board.getSquaresBoard;
import static Board.Board.getkingForColor;
import static Piecs.ChessColor.BLACK;
import static Piecs.ChessColor.WHITE;
import static main.Game.gamePanel;
import static main.Game.getGamePanel;
import static utilz.LoadSave.getLoadSave;

public abstract class Piece {
    private final ChessColor color;
    private Square currentSquare;
    private final BufferedImage PieceImg;
    private boolean isDraw = true;
    private int CurrX;
    private int CurrY;
    private boolean isPinned = false;


    private List<Square> forcedSquare;


    public Piece(ChessColor color, Square currentSquare, BufferedImage pieceImg) {
        this.color = color;
        this.currentSquare = currentSquare;
        PieceImg = pieceImg;
        CurrX = currentSquare.getX();
        CurrY = currentSquare.getY();

        forcedSquare = new ArrayList<>();
    }

    public boolean move(Square fin, Board board) {
        Square[][] squareBoard = getSquaresBoard();
        Piece occup = fin.getOccupyingPiece();
        boolean halfMove = false;

        if (occup != null) {
            if (occup.getColor() == this.color) return false;
            if (this instanceof King king && king.isProtected(fin, board)) {
                System.out.println("Cannot capture protected piece.");
                return false;
            }
            else {
                fin.capture(this, board);
                halfMove = true;//half move rule
            }
        }

        if (this instanceof King king) {
            handleCastling(currentSquare, fin, squareBoard, board, color);
            king.setWasMoved(true);
        }


        currentSquare.removePiece();
        this.currentSquare = fin;
        currentSquare.put(this);

        if (this instanceof Pawn pawn) {
            if (pawn.getColor().equals(ChessColor.WHITE) && this.getPosition().getYNum() == 7) {
                currentSquare.removePiece();
                var Wqueen = new Queen(this.getColor(), this.getPosition(), getLoadSave().getWQueenImg());
                board.getWPieces().add(Wqueen);
                board.getWPieces().remove(this);
                currentSquare.put(Wqueen);
                isDraw = !isDraw;
            } else if (pawn.getColor().equals(ChessColor.BLACK) && this.getPosition().getYNum() == 0) {
                currentSquare.removePiece();
                var Bqueen = new Queen(this.getColor(), this.getPosition(), getLoadSave().getBQueenImg());
                board.getBPieces().add(Bqueen);
                board.getBPieces().remove(this);
                currentSquare.put(Bqueen);
                isDraw = !isDraw;
            }
        }

        if (this instanceof Pawn pawn)
            pawn.setWasMoved(true);

        if (this instanceof Rook rook)
            rook.setWasMoved(true);


        CurrX = currentSquare.getX();
        CurrY = currentSquare.getY();

        if (this.color == ChessColor.WHITE) {
            if (main.MainClass.IsDebugMode())
                System.out.println("you move white piece");
            if (this.getRawLegalMoves().contains(getGamePanel().getBk().getPosition())) {
                System.out.println("calling check");
                getGamePanel().getBk().callCheck();
            }

        } else {
            if (main.MainClass.IsDebugMode())
                System.out.println("you move black piece");
            if (this.getRawLegalMoves().contains(getGamePanel().getWk().getPosition())) {
                System.out.println("calling check");
                getGamePanel().getWk().callCheck();

            }
        }



        gamePanel.getCd().ResetWforcedSquares();
        gamePanel.getCd().ResetBforcedSquares();

        if (this.getColor().equals(ChessColor.WHITE)){
            gamePanel.getCd().getWKing().stopCheck();
        }else
            gamePanel.getCd().getBKing().stopCheck();

        board.getWPieces().forEach(Piece::getRawLegalMoves);
        board.getBPieces().forEach(Piece::getRawLegalMoves);



        if (this instanceof Pawn) { //enPassant capture
            Pawn lastMoved = Board.getBoard().getLastMovedPawn();
            if (lastMoved != null && lastMoved.getColor() != this.getColor()) {
                if (lastMoved.getColor() == BLACK && lastMoved.getPosition().getXNum() == this.getPosition().getXNum() && lastMoved.getPosition().getYNum() == this.getPosition().getYNum() - 1) {
                    lastMoved.getPosition().capture(null, board);
                }
                if (lastMoved.getColor() == WHITE && lastMoved.getPosition().getXNum() == this.getPosition().getXNum() && lastMoved.getPosition().getYNum() == this.getPosition().getYNum() + 1) {
                    lastMoved.getPosition().capture(null, board);
                }
            }
            halfMove = true;//half move rule
        }
        Board.getBoard().setLastMovedPawn(null);

        return halfMove;
    }





    private void handleCastling(Square start, Square fin, Square[][] squaresBoard, Board board, ChessColor color) {
        int startX = start.getXNum();
        int finX = fin.getXNum();
        int y = start.getYNum();
        Square rookEnd = null;
        Square rookStart = null;


        if (color == ChessColor.BLACK) {
            if (finX - startX == 2) {
                System.out.println("Kingside castling");
                rookStart = squaresBoard[y][7];
                rookEnd = squaresBoard[y][finX - 1];


            } else if (startX - finX == 2) {
                System.out.println("Queenside castling");
                rookStart = squaresBoard[y][0];
                rookEnd = squaresBoard[y][finX + 1];

            }

            if (rookEnd == null)
                return;
            if (rookStart == null)
                return;


            board.getBPieces().remove(rookStart.getOccupyingPiece());
            rookStart.getOccupyingPiece().setDraw(false);
            rookStart.removePiece();
            Rook rook = new Rook(color, rookEnd, getLoadSave().getBRookImg());
            rookEnd.put(rook);
            board.getBPieces().add(rook);


        } else if (color == ChessColor.WHITE) {
            if (finX - startX == 2) {
                System.out.println("Kingside castling");
                rookStart = squaresBoard[y][7];
                rookEnd = squaresBoard[y][finX - 1];


            } else if (startX - finX == 2) {
                System.out.println("Queenside castling");
                rookStart = squaresBoard[y][0];
                rookEnd = squaresBoard[y][finX + 1];

            }

            if (rookEnd == null)
                return;
            if (rookStart == null)
                return;


            board.getWPieces().remove(rookStart.getOccupyingPiece());
            rookStart.getOccupyingPiece().setDraw(false);
            rookStart.removePiece();
            Rook rook = new Rook(color, rookEnd, getLoadSave().getWRookImg());
            rookEnd.put(rook);
            board.getWPieces().add(rook);

        }
    }


    private void printBoardState() {
        System.out.println("Current board state:");
        Square[][] squaresBoard = getSquaresBoard();
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                Piece piece = squaresBoard[y][x].getOccupyingPiece();
                if (piece == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(piece.getClass().getSimpleName().charAt(0));
                }
            }
            System.out.println();
        }
    }

    public void Draw(Graphics g) {
        if (isDraw)
            g.drawImage(PieceImg, CurrX, CurrY, 70, 70, null);
    }


    public int[] getLinearOccupations(Square[][] board, int x, int y) {
        int lastYabove = 0;
        int lastXright = 7;
        int lastYbelow = 7;
        int lastXleft = 0;

        for (int i = 0; i < y; i++) {
            if (board[i][x].isOccupied()) {
                if (!(board[i][x].getOccupyingPiece() instanceof King king && king.getColor() != this.getColor()))
                    if (board[i][x].getOccupyingPiece().getColor() != this.color) {
                        lastYabove = i;
                    } else lastYabove = i + 1;
            }
        }

        for (int i = 7; i > y; i--) {
            if (board[i][x].isOccupied()) {
                if (!(board[i][x].getOccupyingPiece() instanceof King king && king.getColor() != this.getColor()))
                    if (board[i][x].getOccupyingPiece().getColor() != this.color) {
                        lastYbelow = i;
                    } else lastYbelow = i - 1;
            }
        }

        for (int i = 0; i < x; i++) {
            if (board[y][i].isOccupied()) {
                if (!(board[i][x].getOccupyingPiece() instanceof King king && king.getColor() != this.getColor()))
                    if (board[y][i].getOccupyingPiece().getColor() != this.color) {
                        lastXleft = i;
                    } else lastXleft = i + 1;
            }
        }

        for (int i = 7; i > x; i--) {
            if (board[y][i].isOccupied()) {
                if (!(board[i][x].getOccupyingPiece() instanceof King king && king.getColor() != this.getColor()))
                    if (board[y][i].getOccupyingPiece().getColor() != this.color) {
                        lastXright = i;
                    } else lastXright = i - 1;
            }
        }

        return new int[]{lastYabove, lastYbelow, lastXleft, lastXright};
    }


    public List<Square> getDiagonalOccupations(Square[][] board, int x, int y) {
        LinkedList<Square> diagOccup = new LinkedList<>();

        int xNW = x - 1;
        int xSW = x - 1;
        int xNE = x + 1;
        int xSE = x + 1;
        int yNW = y - 1;
        int ySW = y + 1;
        int yNE = y - 1;
        int ySE = y + 1;

        while (xNW >= 0 && yNW >= 0) {
            if (board[yNW][xNW].isOccupied() && !(board[yNW][xNW].getOccupyingPiece() instanceof King king && king.getColor() != this.getColor())) {
                if (board[yNW][xNW].getOccupyingPiece().getColor() != this.color) {
                    diagOccup.add(board[yNW][xNW]);
                }
                break;
            } else {
                diagOccup.add(board[yNW][xNW]);
                yNW--;
                xNW--;
            }
        }


        while (xSW >= 0 && ySW < 8) {
            if (board[ySW][xSW].isOccupied() && !(board[ySW][xSW].getOccupyingPiece() instanceof King king && king.getColor() != this.getColor())) {

                if (board[ySW][xSW].getOccupyingPiece().getColor() != this.color) {
                    diagOccup.add(board[ySW][xSW]);
                }
                break;
            } else {
                diagOccup.add(board[ySW][xSW]);
                ySW++;
                xSW--;
            }
        }


        while (xSE < 8 && ySE < 8) {

            if (board[ySE][xSE].isOccupied() && !(board[ySE][xSE].getOccupyingPiece() instanceof King king && king.getColor() != this.getColor())) {

                if (board[ySE][xSE].getOccupyingPiece().getColor() != this.color) {
                    diagOccup.add(board[ySE][xSE]);
                }
                break;
            } else {
                diagOccup.add(board[ySE][xSE]);
                ySE++;
                xSE++;
            }
        }


        while (xNE < 8 && yNE >= 0) {

            if (board[yNE][xNE].isOccupied() && !(board[yNE][xNE].getOccupyingPiece() instanceof King king && king.getColor() != this.getColor())) {

                if (board[yNE][xNE].getOccupyingPiece().getColor() != this.color) {
                    diagOccup.add(board[yNE][xNE]);
                }
                break;
            } else {
                diagOccup.add(board[yNE][xNE]);
                yNE--;
                xNE++;
            }
        }

        return diagOccup;
    }


    public ChessColor getColor() {
        return color;
    }

    public BufferedImage getPieceImg() {
        return PieceImg;
    }

    public Square getPosition() {
        return currentSquare;
    }

    public void setPosition(Square square) {
        if (currentSquare != null) {
            currentSquare.removePiece();
        }
        currentSquare = square;
        if (square != null) {
            square.put(this);
        }
        Board.getBoard().updateKingCheckStatus();
    }

    public void setThePosition(Square square){
        this.currentSquare = square;
    }


    public List<Square> getLegalMoves() {
        List<Square> legalMoves = getRawLegalMoves();

        legalMoves.removeIf(square -> {

            if (square.isOccupied() && square.getOccupyingPiece().getColor() == this.color) {
                return true;
            }

            Square originalPosition = this.getPosition();
            Piece capturedPiece = square.getOccupyingPiece();

            this.setPosition(square);
            Board.getBoard().updateKingCheckStatus();


            boolean inCheck = isKingInCheck();

            if (inCheck) {
                List<Piece> attackers = getGamePanel().getCd().getAttackers(color);


                if (attackers.size() == 1 && attackers.contains(capturedPiece)) {
                    this.setPosition(originalPosition);
                    if (capturedPiece != null) {
                        square.put(capturedPiece);
                    }
                    return false;
                }


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

    private boolean isKingInCheck() {
        King king = getkingForColor(this.color);
        return king.isInCheck();
    }


    public abstract List<Square> getRawLegalMoves();


    public int getCurrX() {
        return CurrX;
    }

    public Piece setCurrX(int currX) {
        CurrX = currX;
        return this;
    }

    public int getCurrY() {
        return CurrY;
    }

    public Piece setCurrY(int currY) {
        CurrY = currY;
        return this;
    }

    public boolean isForcedSquare() {
        return !forcedSquare.isEmpty();
    }

    public List<Square> getForcedSquare() {
        return forcedSquare;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public boolean isForcedSquares() {
        return gamePanel.isForcedSquaresForColor(this.getColor());
    }
    public char toFEN() {
        char key = switch (this.getClass().getSimpleName()) {
            case "Pawn" -> 'P';
            case "Rook" -> 'R';
            case "Knight" -> 'N';
            case "Bishop" -> 'B';
            case "Queen" -> 'Q';
            case "King" -> 'K';
            default -> ' ';
        };
        return this.getColor().equals(BLACK) ? Character.toLowerCase(key) : key;
    }
}
