package main;
import javax.swing.JFrame;

public class GameWindow {
    public static int WINDOW_WIDTH = 1240 , WINDOW_HEIGHT = 800;

	public GameWindow(GamePanel gamePanel) {

        JFrame jframe = new JFrame("Chess Game");
		jframe.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(gamePanel);
		jframe.setLocationRelativeTo(null);
		jframe.setResizable(false);
		jframe.setVisible(true);

	}

}
