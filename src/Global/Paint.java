package Global;

import Board.Board;
import Board.Clock;
import Board.Square;
import Detectores.CheckDetector;
import Piecs.ChessColor;
import Piecs.Piece;
import inputs.MouseInputs;

import java.awt.*;

import static main.GamePanel.BOARD_HEIGHT;
import static main.GamePanel.BOARD_WIDTH;
import static main.GameWindow.WINDOW_HEIGHT;
import static main.GameWindow.WINDOW_WIDTH;
import static utilz.LoadSave.loadSave;

public class Paint {

    public static void PaintLogin(Graphics g){

    }

    public static void PaintGame(Graphics g, Graphics2D g2d, Color grey, MouseInputs mouseInputs, Clock WClock, Clock BClock, Board board, ChessColor turn, CheckDetector cd){
        g2d.setStroke(new BasicStroke(5));

        g.drawImage(loadSave.getBOARD_IMG(), WINDOW_WIDTH - 800, WINDOW_HEIGHT - 770, BOARD_WIDTH, BOARD_HEIGHT, null);
        g.fillRect(0, 0, WINDOW_WIDTH - 850, WINDOW_HEIGHT);
        g.drawImage(loadSave.getLogoImg(), 130, 30, 150, 150, null);

        g.setColor(Color.WHITE);


        g.setColor(Color.WHITE);
        g.drawString("  -♙♖♘♗♕♔♗♘♖♙---------Chess Nate --------♟♜♞♝♛♚♝♞♜♟-  ", 0, 200);


        g.setColor(grey);
        if (!(mouseInputs.currPiece() == null))
            if (mouseInputs.currPiece().getColor() == ChessColor.WHITE && mouseInputs.isWhiteTurn() ||
                    mouseInputs.currPiece().getColor() == ChessColor.BLACK && !mouseInputs.isWhiteTurn())
                for (Square square : mouseInputs.currPiece().getLegalMoves())

                    if (mouseInputs.EnableDots())
                        if (!square.isOccupied())
                            g.fillOval(square.getX() + 18, square.getY() + 18, 30, 30);
                        else if (square.getOccupyingPiece().getColor() != mouseInputs.currPiece().getColor())
                            g2d.drawOval(square.getX() - 7, square.getY() - 10, 85, 85);


        g.drawImage(loadSave.getStockfishLogo(),230,250,100,100,null);
        g.setColor(Color.WHITE);
        g.fillRect(200, 360, 120, 50);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(BClock.toString(), 210, 390);

        g.drawString("---------------------------  VS -------------------------", 0, 470);


        g.drawImage(loadSave.getPlayerIcon(),230,520,100,100,null);
        g.setColor(Color.WHITE);
        g.fillRect(200, 640, 120, 50);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(WClock.toString(), 210, 670);


        for (Piece Wpiece : board.getBPieces())
            Wpiece.Draw(g);

        for (Piece Bpiece : board.getWPieces())
            Bpiece.Draw(g);

        turn = mouseInputs.isWhiteTurn() ? ChessColor.WHITE : ChessColor.BLACK;
        cd.restrictMovesInCheck(board, turn);


    }
}
