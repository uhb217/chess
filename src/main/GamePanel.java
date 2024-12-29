package main;

import Board.Board;
import Board.Square;
import Detectores.CheckDetector;
import Global.Paint;
import Piecs.*;
import inputs.*;
import utilz.LoadSave;
import Board.Clock;

import java.awt.*;
import javax.swing.JPanel;

import static Board.Board.getBoard;
import static Board.Board.getSquaresBoard;

import static main.GameWindow.WINDOW_HEIGHT;
import static main.GameWindow.WINDOW_WIDTH;
import static utilz.LoadSave.getLoadSave;

public class GamePanel extends JPanel {

    private final Board board;
    private final Square[][] SquaresBoard;
    private final LoadSave loadSave;
    public static int BOARD_WIDTH = 700, BOARD_HEIGHT = 700;
   private State state;

    final Clock WClock;
    final Clock BClock;

    boolean bp = true;
    boolean wp = true;

    private King wk;
    private King bk;

    private boolean isInit;

    final Color grey = new Color(160, 160, 164, 73);

    MouseInputs mouseInputs;
    private final CheckDetector cd;

    ChessColor turn;


    public GamePanel() {
        loadSave = getLoadSave();
        board = getBoard();
        SquaresBoard = getSquaresBoard();
        mouseInputs = new MouseInputs(board);
        WClock = new Clock(10, 0, ChessColor.WHITE);
        BClock = new Clock(10, 0, ChessColor.BLACK);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        initializePieces();
        state = State.Game;
        cd = new CheckDetector(this);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        switch (state){
            case Game:
                Paint.PaintGame(g,g2d,grey,mouseInputs,WClock,BClock,board,turn,cd);
            case Login:
                Paint.PaintLogin(g);
        }

    }


    private void initializePieces() {

        wk = new King(ChessColor.WHITE, SquaresBoard[0][4], loadSave.getWKingImg());
        bk = new King(ChessColor.BLACK, SquaresBoard[7][4], loadSave.getBKingImg());

        SquaresBoard[7][4].put(bk);
        SquaresBoard[0][4].put(wk);

        for (int x = 0; x < 8; x++) {
            SquaresBoard[1][x].put(new Pawn(ChessColor.WHITE, SquaresBoard[1][x], loadSave.getWPawnImg()));
            SquaresBoard[6][x].put(new Pawn(ChessColor.BLACK, SquaresBoard[6][x], loadSave.getBPawnImg()));
        }

        SquaresBoard[7][3].put(new Queen(ChessColor.BLACK, SquaresBoard[7][3], loadSave.getBQueenImg()));
        SquaresBoard[0][3].put(new Queen(ChessColor.WHITE, SquaresBoard[0][3], loadSave.getWQueenImg()));


        SquaresBoard[0][0].put(new Rook(ChessColor.WHITE, SquaresBoard[0][0], loadSave.getWRookImg()));
        SquaresBoard[0][7].put(new Rook(ChessColor.WHITE, SquaresBoard[0][7], loadSave.getWRookImg()));
        SquaresBoard[7][0].put(new Rook(ChessColor.BLACK, SquaresBoard[7][0], loadSave.getBRookImg()));
        SquaresBoard[7][7].put(new Rook(ChessColor.BLACK, SquaresBoard[7][7], loadSave.getBRookImg()));

        SquaresBoard[0][1].put(new Knight(ChessColor.WHITE, SquaresBoard[0][1], loadSave.getWNightImg()));
        SquaresBoard[0][6].put(new Knight(ChessColor.WHITE, SquaresBoard[0][6], loadSave.getWNightImg()));
        SquaresBoard[7][1].put(new Knight(ChessColor.BLACK, SquaresBoard[7][1], loadSave.getBNightImg()));
        SquaresBoard[7][6].put(new Knight(ChessColor.BLACK, SquaresBoard[7][6], loadSave.getBNightImg()));

        SquaresBoard[0][2].put(new Bishop(ChessColor.WHITE, SquaresBoard[0][2], loadSave.getWBishopImg()));
        SquaresBoard[0][5].put(new Bishop(ChessColor.WHITE, SquaresBoard[0][5], loadSave.getWBishopImg()));
        SquaresBoard[7][2].put(new Bishop(ChessColor.BLACK, SquaresBoard[7][2], loadSave.getBBishopImg()));
        SquaresBoard[7][5].put(new Bishop(ChessColor.BLACK, SquaresBoard[7][5], loadSave.getBBishopImg()));


        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                board.getWPieces().add(SquaresBoard[y][x].getOccupyingPiece());
                board.getBPieces().add(SquaresBoard[7 - y][x].getOccupyingPiece());
            }
        }

        for (Piece Wp : board.getWPieces()) {
            if (Wp == null) {
                wp = false;
                break;
            }
        }

        for (Piece Bp : board.getBPieces()) {
            if (Bp == null) {
                bp = false;
                break;
            }
        }

        if (bp && wp) {
            isInit = true;

        }
    }


    public boolean isForcedSquaresForColor(ChessColor color) {
        boolean isForced = false;
        if (color.equals(ChessColor.WHITE)) {
            if (!cd.getWforcedSquares().isEmpty())
                isForced = true;
        } else {
            if (!cd.getBforcedSquares().isEmpty())
                isForced = true;
        }

        return isForced;
    }


    public Board board() {
        return board;
    }

    public boolean isWhiteTurn() {
        return mouseInputs.isWhiteTurn();
    }

    public LoadSave getloadSave() {
        return loadSave;
    }

    public static int getBOARD_WIDTH() {
        return BOARD_WIDTH;
    }

    public static int getBOARD_HEIGHT() {
        return BOARD_HEIGHT;
    }

    public Color getGrey() {
        return grey;
    }

    public MouseInputs getMouseInputs() {
        return mouseInputs;
    }

    public King getWk() {
        if (isInit)
            return wk;
        System.out.println("is init?:" + isInit);
        return null;
    }

    public King getBk() {
        if (isInit)
            return bk;
        System.out.println("is init?:" + isInit);
        return null;
    }

    public boolean isInit() {
        return isInit;
    }

    public CheckDetector getCd() {
        return cd;
    }

    public Clock WClock() {
        return WClock;
    }

    public Clock BClock() {
        return BClock;
    }
}

