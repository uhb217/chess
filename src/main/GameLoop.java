package main;

import Board.Clock;

public class GameLoop {
    private final int FPS = 15;
    private final boolean SHOW_FPS = false;

    public void run(GamePanel gamePanel) {
        new Thread(() -> {
            double timePerFrame = 1000000000.0 / FPS;
            long previousTime = System.nanoTime();
            int frames = 0;
            long lastCheck = System.currentTimeMillis();
            double deltaFrames = 0;

            while (true) {
                long currentTime = System.nanoTime();
                deltaFrames += (currentTime - previousTime) / timePerFrame;
                previousTime = currentTime;

                if (deltaFrames >= 1) {
                    gamePanel.repaint();
                    frames++;
                    deltaFrames--;
                }

                if (SHOW_FPS) {
                    if (System.currentTimeMillis() - lastCheck >= 1000) {
                        lastCheck = System.currentTimeMillis();
                        System.out.println("FPS:" + frames);
                        frames = 0;
                    }
                }
            }
        }).start();
    }


}
