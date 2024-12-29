package Detectores;

import Board.Board;
import Piecs.*;
import Board.Square;
import main.GamePanel;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

public class CheckDetector {
    private King WKing;
    private King BKing;
    private final Board board;
    private final Square[][] SquaresBoard;
    private HashMap<Piece, List<Square>> WforcedSquares;
    private  HashMap<Piece, List<Square>> BforcedSquares;
    ArrayList<Piece> WPieces;
    ArrayList<Piece> BPieces;



    public CheckDetector(GamePanel panel) {
        this.board = Board.getBoard();
        SquaresBoard = Board.getSquaresBoard();
        WforcedSquares = new HashMap<>();
        BforcedSquares = new HashMap<>();
        WPieces = board.getWPieces();
        BPieces = board.getBPieces();

        WKing = panel.getWk();
        BKing = panel.getBk();

    }


    public boolean checkForMate() {
        if (WKing != null && BKing != null) {
            if (WKing.isInCheck()) {
                if (!cabMove(ChessColor.WHITE))
                    if (!canCapture(ChessColor.WHITE))
                        if (!CanBlock(ChessColor.WHITE)) {
                            CheckMate.callCheckMate(ChessColor.BLACK);
                            return true;
                        }


            } else if (BKing.isInCheck()) {
                if (!cabMove(ChessColor.BLACK))
                    if (!canCapture(ChessColor.BLACK))
                        if (!CanBlock(ChessColor.BLACK)) {
                            CheckMate.callCheckMate(ChessColor.WHITE);
                            return true;
                        }
            }
        } else
            System.out.println("one of the kings is null");
        return false;
    }

    private boolean CanBlock(ChessColor color) {
        List<Piece> attackers = getAttackers(color);
        if (attackers.size() != 1) return false;

        Piece attacker = attackers.get(0);
        List<Square> checkPath = getCheckPath(attacker);

        List<Piece> allies = color == ChessColor.WHITE ? WPieces : BPieces;
        for (Piece ally : allies) {
            for (Square move : ally.getLegalMoves()) {
                if (checkPath.contains(move)) {
                    ally.getForcedSquare().add(move);
                    return true;
                }
            }
        }
        return false;
    }



    private boolean cabMove(ChessColor color) {
        return !getkingForColor(color).getLegalMoves().isEmpty();
    }


    public List<Square> getProtectionSquares(Piece attacker, Piece king) {
        List<Square> protectionSquares = new ArrayList<>();

        if (attacker instanceof Knight) {
            protectionSquares.add(attacker.getPosition());
            return protectionSquares;
        }

        protectionSquares.add(attacker.getPosition());

        if (attacker instanceof Rook || attacker instanceof Bishop || attacker instanceof Queen) {
            List<Square> path = getPathBetween(attacker.getPosition(), king.getPosition());
            protectionSquares.addAll(path);
        }

        return protectionSquares;
    }

    public List<Square> filterLegalMovesInCheck(ChessColor color) {
        King king = color == ChessColor.WHITE ? WKing : BKing;


        if (king != null) {
            if (!king.isInCheck()) return null;

            List<Piece> attackers = getAttackers(color);

            if (attackers.size() != 1)
                return king.getLegalMoves();


            Piece attacker = attackers.get(0);
            return new ArrayList<>(getProtectionSquares(attacker, king));
        }
        return List.of();
    }



    public void restrictMovesInCheck(Board board, ChessColor currentTurn) {
        List<Square> validSquares = filterLegalMovesInCheck(currentTurn);
        if (validSquares == null)  return;
        List<Piece> allies = currentTurn == ChessColor.WHITE ? board.getWPieces() : board.getBPieces();
        for (Piece ally : allies) {
            ally.getLegalMoves().removeIf(move -> !validSquares.contains(move));
        }
    }






    public List<Square> getPathBetween(Square from, Square to) {
        List<Square> path = new ArrayList<>();

        int dx = Integer.compare(to.getXNum(), from.getXNum());
        int dy = Integer.compare(to.getYNum(), from.getYNum());

        int x = from.getXNum() + dx;
        int y = from.getYNum() + dy;

        while (x != to.getXNum() || y != to.getYNum()) {
            path.add(Board.getSquaresBoard()[y][x]);
            x += dx;
            y += dy;
        }

        return path;
    }







    public Piece getkingForColor(ChessColor color) {
        if (color.equals(ChessColor.WHITE))
            return WKing;
        else return BKing;
    }


    private boolean canCapture(ChessColor color) {
        List<Piece> checkingPieces = getAttackers(color);
        if (checkingPieces.size() != 1)
            return false;

        var Attker = checkingPieces.get(0);

        if (color == ChessColor.WHITE) {

            for (Piece WPiece : WPieces)
                if (WPiece.getLegalMoves().contains(Attker.getPosition())) {
                    WPiece.getForcedSquare().add(Attker.getPosition());
                    WforcedSquares.put(WPiece, WPiece.getForcedSquare());
                }

            return !WforcedSquares.isEmpty();

        } else {
            for (Piece BPiece : BPieces)
                if (BPiece.getLegalMoves().contains(Attker.getPosition())) {
                    BPiece.getForcedSquare().add(Attker.getPosition());
                    BforcedSquares.put(BPiece, BPiece.getForcedSquare());
                }

            return !BforcedSquares.isEmpty();

        }
    }


    private List<Square> getCheckPath(Piece attacker) {
        List<Square> checkPath = new ArrayList<>();

        if (attacker instanceof Queen) {
            checkPath.addAll(getStraightLinePath(attacker));
            checkPath.addAll(getDiagonalPath(attacker));
        } else if (attacker instanceof Rook) {
            checkPath.addAll(getStraightLinePath(attacker));
        } else if (attacker instanceof Bishop) {
            checkPath.addAll(getDiagonalPath(attacker));
        } else if (attacker instanceof Knight || attacker instanceof Pawn) {
            checkPath.add(attacker.getPosition());
        }

        return checkPath;
    }


    private List<Square> getStraightLinePath(Piece Attaker) {
        List<Square> path = new ArrayList<>();
        int x1 = Attaker.getPosition().getXNum();
        int y1 = Attaker.getPosition().getYNum();
        int x2 ;
        int y2;

        if (Attaker.getColor().equals(ChessColor.WHITE)) {
             x2 = BKing.getPosition().getXNum();
             y2 = BKing.getPosition().getYNum();
        }else {
            x2 = WKing.getPosition().getXNum();
            y2 = WKing.getPosition().getYNum();
        }


        if (x1 == x2) {
            int minY = Math.min(y1, y2);
            int maxY = Math.max(y1, y2);
            for (int y = minY + 1; y < maxY; y++) {
                path.add(SquaresBoard[y][x1]);
            }
        } else if (y1 == y2) {

            int minX = Math.min(x1, x2);
            int maxX = Math.max(x1, x2);
            for (int x = minX + 1; x < maxX; x++) {
                path.add(SquaresBoard[y2][x]);
            }
        }

        return path;
    }


    private List<Square> getDiagonalPath(Piece Attaker) {
        List<Square> path = new ArrayList<>();
        int x1 = Attaker.getPosition().getXNum();
        int y1 = Attaker.getPosition().getYNum();
        int x2;
        int y2 ;

        if (Attaker.getColor().equals(ChessColor.WHITE)) {
            x2 = BKing.getPosition().getXNum();
            y2 = BKing.getPosition().getYNum();
        }else {
            x2 = WKing.getPosition().getXNum();
            y2 = WKing.getPosition().getYNum();
        }

        int dx = Integer.signum(x2 - x1);
        int dy = Integer.signum(y2 - y1);

        int x = x1 + dx;
        int y = y1 + dy;
        while (x != x2 && y != y2) {
            path.add(SquaresBoard[y][x]);
            x += dx;
            y += dy;
        }

        return path;
    }



    public List<Piece> getAttackers(ChessColor color) {

        List<Piece> checkingPieces = new ArrayList<>();

        if (color.equals(ChessColor.WHITE)) {
            for (Piece BPiece : BPieces) {
                if (BPiece.getLegalMoves().contains(WKing.getPosition()))
                    checkingPieces.add(BPiece);
            }
        } else {
            for (Piece WPiece : WPieces) {
                if (WPiece.getRawLegalMoves().contains(BKing.getPosition()))
                    checkingPieces.add(WPiece);
            }
        }
        return checkingPieces;

    }

    public King getWKing() {
        return WKing;
    }

    public King getBKing() {
        return BKing;
    }

    public HashMap<Piece, List<Square>> getWforcedSquares() {
        return WforcedSquares;
    }

    public HashMap<Piece, List<Square>> getBforcedSquares() {
        return BforcedSquares;
    }


    public void ResetWforcedSquares() {
        BforcedSquares.clear();
    }
    public void ResetBforcedSquares() {
        BforcedSquares.clear();
    }
}






