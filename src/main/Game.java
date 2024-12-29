package main;

import Detectores.CheckDetector;

import javax.swing.*;

public class Game {
  public static GamePanel gamePanel;
    public Game() {
        gamePanel = new GamePanel();
        new GameWindow(gamePanel);
        gamePanel.requestFocus();
        SwingUtilities.invokeLater(() -> new GameLoop().run(gamePanel));
        startClocks();


    }

    private void startClocks() {
        gamePanel.WClock.startClock(gamePanel);
        gamePanel.BClock.startClock(gamePanel);
    }

    public static GamePanel getGamePanel() {
        return gamePanel;
    }



}
